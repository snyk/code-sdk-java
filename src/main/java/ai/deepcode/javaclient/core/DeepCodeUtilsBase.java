package ai.deepcode.javaclient.core;

import ai.deepcode.javaclient.DeepCodeRestApi;
import ai.deepcode.javaclient.responses.GetFiltersResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class DeepCodeUtilsBase {

  private final AnalysisDataBase analysisData;
  private final DeepCodeParamsBase deepCodeParams;
  private final DeepCodeIgnoreInfoHolderBase ignoreInfoHolder;
  private final PlatformDependentUtilsBase pdUtils;
  private final DCLoggerBase dcLogger;
  private final DeepCodeRestApi restApi;

  protected DeepCodeUtilsBase(
    @NotNull AnalysisDataBase analysisData,
    @NotNull DeepCodeParamsBase deepCodeParams,
    @NotNull DeepCodeIgnoreInfoHolderBase ignoreInfoHolder,
    @NotNull PlatformDependentUtilsBase pdUtils,
    @NotNull DCLoggerBase dcLogger,
    @NotNull DeepCodeRestApi restApi
  ) {
    this.analysisData = analysisData;
    this.deepCodeParams = deepCodeParams;
    this.ignoreInfoHolder = ignoreInfoHolder;
    this.pdUtils = pdUtils;
    this.dcLogger = dcLogger;
    this.restApi = restApi;
    initSupportedExtentionsAndConfigFiles();
  }

  protected static Set<String> supportedExtensions = Collections.emptySet();
  protected static Set<String> supportedConfigFiles = Collections.emptySet();

  public List<Object> getAllSupportedFilesInProject(
    @NotNull Object project, boolean scanAllMissedIgnoreFile, @Nullable Object progress) {
    final Collection<Object> allProjectFiles = allProjectFiles(project);
    if (allProjectFiles.isEmpty()) {
      dcLogger.logWarn("Empty files list for project: " + project);
    }
    if (scanAllMissedIgnoreFile) {
      ignoreInfoHolder.scanAllMissedIgnoreFiles(allProjectFiles, progress);
    }

    dcLogger.logInfo("ReScan for All ignored files at: " + project);
    int counter = 0;
    final int totalSize = allProjectFiles.size();
    final List<Object> result = new ArrayList<>();
    for (Object file : allProjectFiles) {
      pdUtils.progressSetText(
        progress, "Checked if supported " + counter + " files of " + totalSize);
      pdUtils.progressSetFraction(progress, ((double) counter++ / totalSize));
      if (isSupportedFileFormat(file)) {
        result.add(file);
      }
      pdUtils.progressCheckCanceled(progress);
    }
    dcLogger.logInfo("ReScan for All ignored files FINISHED for: " + project);
    // clean up cashes built without .ignore files parsing
    if (!scanAllMissedIgnoreFile) ignoreInfoHolder.removeProject(project);

    if (result.isEmpty()) dcLogger.logWarn("Empty supported files list for project: " + project);
    return result;
  }

  protected abstract Collection<Object> allProjectFiles(@NotNull Object project);

  private static final long MAX_FILE_SIZE = 1024*1024; // 1MB in bytes

  public boolean isSupportedFileFormat(@NotNull Object file) {
    // DCLogger.getInstance().info("isSupportedFileFormat started for " + psiFile.getName());
    if (ignoreInfoHolder.isIgnoredFile(file) || isGitIgnoredExternalCheck(file)) return false;
    long fileLength = getFileLength(file);
    boolean supported = 0 < fileLength && fileLength < MAX_FILE_SIZE &&
      (supportedExtensions.contains(getFileExtention(file)) || supportedConfigFiles.contains(pdUtils.getFileName(file)));
    // DCLogger.getInstance().info("isSupportedFileFormat ends for " + psiFile.getName());
    return supported;
  }

  protected abstract long getFileLength(@NotNull Object file);

  protected abstract String getFileExtention(@NotNull Object file);

  protected abstract boolean isGitIgnoredExternalCheck(@NotNull Object file);

  /**
   * Potentially <b>Heavy</b> network request!
   */
  private void initSupportedExtentionsAndConfigFiles() {
    GetFiltersResponse filtersResponse =
      restApi.getFilters(deepCodeParams.getSessionToken());
    if (filtersResponse.getStatusCode() == 200) {
      supportedExtensions =
        filtersResponse.getExtensions().stream()
          .map(s -> s.substring(1)) // remove preceding `.` (`.js` -> `js`)
          .collect(Collectors.toSet());
      supportedConfigFiles = new HashSet<>(filtersResponse.getConfigFiles());
      dcLogger.logInfo("Supported extensions: " + supportedExtensions);
      dcLogger.logInfo("Supported configFiles: " + supportedConfigFiles);
    } else {
      dcLogger.logWarn(
        "Can't retrieve supported file extensions and config files from the server. Fallback to default set.\n"
          + filtersResponse.getStatusCode()
          + " "
          + filtersResponse.getStatusDescription());
      supportedExtensions =
        new HashSet<>(
          Arrays.asList(
            "cc", "htm", "cpp", "cxx", "c", "vue", "h", "hpp", "hxx", "es6", "js", "py", "es",
            "jsx", "java", "tsx", "html", "ts"));
      supportedConfigFiles =
        new HashSet<>(
          Arrays.asList(
            "pylintrc",
            "ruleset.xml",
            ".eslintrc.json",
            ".pylintrc",
            ".eslintrc.js",
            "tslint.json",
            ".pmdrc.xml",
            ".ruleset.xml",
            ".eslintrc.yml"));
    }
  }

  // todo mapFile2EWI at AnalysisData
  public ErrorsWarningsInfos getEWI(Collection<Object> files) {
    int errors = 0;
    int warnings = 0;
    int infos = 0;
    Set<String> countedSuggestions = new HashSet<>();
    for (Object file : files) {
      for (SuggestionForFile suggestion : analysisData.getAnalysis(file)) {
        if (!countedSuggestions.contains(suggestion.getId())) {
          final int severity = suggestion.getSeverity();
          if (severity == 1) infos += 1;
          else if (severity == 2) warnings += 1;
          else if (severity == 3) errors += 1;
          countedSuggestions.add(suggestion.getId());
        }
      }
    }
    return new ErrorsWarningsInfos(errors, warnings, infos);
  }

  public static class ErrorsWarningsInfos {
    private final int errors;
    private final int warnings;
    private final int infos;

    public ErrorsWarningsInfos(int errors, int warnings, int infos) {
      this.errors = errors;
      this.warnings = warnings;
      this.infos = infos;
    }

    public int getErrors() {
      return errors;
    }

    public int getWarnings() {
      return warnings;
    }

    public int getInfos() {
      return infos;
    }
  }
}
