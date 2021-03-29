package ai.deepcode.javaclient.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.PatternSyntaxException;

public abstract class DeepCodeIgnoreInfoHolderBase {

  private final HashContentUtilsBase hashContentUtils;
  private final PlatformDependentUtilsBase pdUtils;
  private final DCLoggerBase dcLogger;

  private static final Map<Object, Set<PathMatcher>> map_ignore2PathMatchers = new ConcurrentHashMap<>();

  private static final Map<Object, Set<PathMatcher>> map_ignore2ReIncludePathMatchers = new ConcurrentHashMap<>();

  private static final Map<Object, Map<String, Boolean>> project2IgnoredFilePaths = new ConcurrentHashMap<>();

  protected DeepCodeIgnoreInfoHolderBase(
          @NotNull HashContentUtilsBase hashContentUtils,
          PlatformDependentUtilsBase pdUtils, @NotNull DCLoggerBase dcLogger) {
    this.hashContentUtils = hashContentUtils;
    this.pdUtils = pdUtils;
    this.dcLogger = dcLogger;
  }

  public void scanAllMissedIgnoreFiles(
          @NotNull Collection<Object> allProjectFiles,
          @Nullable Object progress) {
    allProjectFiles.stream()
            .filter(this::is_ignoreFile)
            .filter(ignoreFile -> !map_ignore2PathMatchers.containsKey(ignoreFile))
            .forEach(ignoreFile -> update_ignoreFileContent(ignoreFile, allProjectFiles, progress));
  }

  public boolean isIgnoredFile(@NotNull Object file) {
    return project2IgnoredFilePaths
        .computeIfAbsent(pdUtils.getProject(file), prj -> new ConcurrentHashMap<>())
        .computeIfAbsent(
            pdUtils.getFilePath(file),
            filePath -> isMatchForMap(file, map_ignore2PathMatchers, filePath) &&
                        !isMatchForMap(file, map_ignore2ReIncludePathMatchers, filePath)
        );
  }

  private boolean isMatchForMap(Object file, @NotNull Map<Object, Set<PathMatcher>> map, String filePath) {
    final Path path = pathOf(filePath);
    return map.entrySet().stream()
            .filter(e -> inScope(e.getKey(), file))
            .flatMap(e -> e.getValue().stream())
            .anyMatch(it -> it.matches(path));
  }

  private void removeIgnoredFilePaths(@NotNull Object ignoreFile) {
    final Object project = pdUtils.getProject(ignoreFile);
    project2IgnoredFilePaths
            .get(project)
            .keySet()
            .removeIf(filePath -> inScope(ignoreFile, filePath));
  }

  /** copy of {@link Path#of(java.lang.String, java.lang.String...)} due to java 8 compatibility */
  private static Path pathOf(String first, String... more){
    return FileSystems.getDefault().getPath(first, more);
  }

  private boolean inScope(@NotNull Object ignoreFile, @NotNull Object fileToCheck) {
    return inScope(ignoreFile, pdUtils.getFilePath(fileToCheck));
  };

  private boolean inScope(@NotNull Object ignoreFile, @NotNull String filePathToCheck) {
    return filePathToCheck.startsWith(pdUtils.getDirPath(ignoreFile));
  };

  public boolean is_ignoreFile(@NotNull Object file) {
    return is_dcignoreFile(file) || is_gitignoreFile(file);
  }

  public boolean is_dcignoreFile(@NotNull Object file) {
    return pdUtils.getFileName(file).equals(".dcignore");
  }

  public boolean is_gitignoreFile(@NotNull Object file) {
    return pdUtils.getFileName(file).equals(".gitignore");
  }

  public void remove_ignoreFileContent(@NotNull Object ignoreFile) {
    removeIgnoredFilePaths(ignoreFile);
    map_ignore2PathMatchers.remove(ignoreFile);
    map_ignore2ReIncludePathMatchers.remove(ignoreFile);
  }

  public void removeProject(@NotNull Object project) {
    map_ignore2PathMatchers.keySet().forEach(file -> {
      if (pdUtils.getProject(file).equals(project)) remove_ignoreFileContent(file);
    });
    map_ignore2ReIncludePathMatchers.keySet().forEach(file -> {
      if (pdUtils.getProject(file).equals(project)) remove_ignoreFileContent(file);
    });
    project2IgnoredFilePaths.remove(project);
  }

  public void update_ignoreFileContent(@NotNull Object ignoreFile, @NotNull Collection<Object> allProjectFiles, @Nullable Object progress) {
    dcLogger.logInfo("Scanning .ignore file: " + pdUtils.getFilePath(ignoreFile));
    parse_ignoreFile2Globs(ignoreFile, progress);
    dcLogger.logInfo("Scan FINISHED for .ignore file: " + pdUtils.getFilePath(ignoreFile));
  }

  private void parse_ignoreFile2Globs(@NotNull Object file, @Nullable Object progress) {
    pdUtils.progressSetText(progress, "parsing file: " + pdUtils.getFilePath(file));
    Set<PathMatcher> ignoreSet = new HashSet<>();
    Set<PathMatcher> reIncludedSet = new HashSet<>();
    String basePath = pdUtils.getDirPath(file);
    String lineSeparator = "[\n\r]";
    final String fileText = hashContentUtils.doGetFileContent(file);
    final String[] lines = fileText.split(lineSeparator);
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];

      // https://git-scm.com/docs/gitignore#_pattern_format
      line = line.trim();
      if (line.isEmpty() || line.startsWith("#")) continue;

      // An optional prefix "!" which negates the pattern;
      // any matching file excluded by a previous pattern will become included again.
      // todo??? It is not possible to re-include a file if a parent directory of that file is excluded.
      boolean isReIncludePattern = line.startsWith("!");
      if (isReIncludePattern) line = line.substring(1);

      String prefix = basePath;
      // If there is a separator at the beginning or middle (or both) of the pattern, then the
      // pattern is relative to the directory level of the particular .gitignore file itself.
      // Otherwise the pattern may also match at any level below the .gitignore level.
      int indexBegMidSepar = line.substring(0, line.length() - 1).indexOf('/');
      if (indexBegMidSepar != 0) prefix += "/";
      if (indexBegMidSepar == -1) {
        prefix += "**/";
      } else if (line.endsWith("/*") || line.endsWith("/**")) {
        int indexLastSepar = line.lastIndexOf('/');
        if (indexBegMidSepar == indexLastSepar) prefix += "**/";
      }

      // If there is a separator at the end of the pattern then the pattern will only match
      // directories, otherwise the pattern can match both files and directories.
      String postfix =
              (line.endsWith("/"))
                      ? "?**" // should be dir
                      : "{/?**,}"; // could be dir or file

      // glob sanity check for validity
      try {
        PathMatcher globToMatch = FileSystems.getDefault()
                .getPathMatcher("glob:" + prefix + line + postfix);

        if (isReIncludePattern) {
          reIncludedSet.add(globToMatch);
        } else {
          ignoreSet.add(globToMatch);
        }
      } catch (PatternSyntaxException e) {
        dcLogger.logWarn("Incorrect Glob syntax in .ignore file: " + e.getMessage());
      }
      pdUtils.progressSetFraction(progress, (double) i/lines.length);
      pdUtils.progressCheckCanceled(progress);
    }
    map_ignore2ReIncludePathMatchers.put(file, reIncludedSet);
    map_ignore2PathMatchers.put(file, ignoreSet);
  }
}
