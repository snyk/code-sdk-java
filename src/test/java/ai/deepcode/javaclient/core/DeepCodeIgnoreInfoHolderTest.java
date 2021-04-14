package ai.deepcode.javaclient.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class DeepCodeIgnoreInfoHolderTest {

  private final File basicIgnoreFile = new File(getClass().getClassLoader().getResource("basic/.dcignore").getPath());
  private final File basicProject = basicIgnoreFile.getParentFile();

  private final File fullDcignoreFile = new File(getClass().getClassLoader().getResource("full/.dcignore").getPath());
  private final File fullDcignoreProject = fullDcignoreFile.getParentFile();

  @Test
  public void ignoreFileNaming() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();
    assertTrue(ignoreInfoHolder.is_dcignoreFile(new File(basicProject, ".dcignore")));
    assertTrue(ignoreInfoHolder.is_gitignoreFile(new File(basicProject, ".gitignore")));
    assertTrue(ignoreInfoHolder.is_ignoreFile(new File(basicProject, ".dcignore")));
    assertTrue(ignoreInfoHolder.is_ignoreFile(new File(basicProject, "blabla/.dcignore")));
  }

  @Test
  public void basicIgnoreFile() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "anyfile.js")));

    ignoreInfoHolder.update_ignoreFileContent(basicIgnoreFile, null);
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "2.js")));
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "1.js")));
  }

  @Test
  public void fullIgnoreFile() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();

    assertTrue(fullDcignoreFile.exists());
    ignoreInfoHolder.update_ignoreFileContent(fullDcignoreFile, null);

    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject,"1.js")));
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject,"scripts/1.js")));
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject,"node_modules/1.js")));
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject,"node_modules/1/1/1.js")));

    // # Hidden directories
    // .*/
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "1/.1/1.js")));

    // # Godot
    // data_*/
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "1/data_1/1.js")));

    // # Lilypond
    // *~
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "1/1~/1.js")));

    // # Python
    // /site
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "site/1.js")));
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "1/site/1.js")));

    // # Unity
    // /[Ll]ibrary/
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "library/1.js")));
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "Kibrary/1.js")));

    // # VisualStudio
    // ~$*
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "~$1/1.js")));

    // # Emacs
    // \#*\#
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "#1#")));

    // # Magento1
    // /media/*
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "media/1.js")));
    // !/media/dhl
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "media/dhl")));
    // /media/dhl/*
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "media/dhl/1.js")));

    // # IAR_EWARM
    // EWARM/**/Obj
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(fullDcignoreProject, "EWARM/1/1/Obj/1.js")));
  }

  @Test
  public void removeIgnoreFile() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();
    ignoreInfoHolder.update_ignoreFileContent(basicIgnoreFile, null);

    ignoreInfoHolder.remove_ignoreFileContent(basicIgnoreFile);
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "2.js")));
  }

  @Test
  public void removeProject() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();
    ignoreInfoHolder.update_ignoreFileContent(basicIgnoreFile, null);

    ignoreInfoHolder.removeProject(basicProject);
    assertFalse(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "2.js")));
  }

  @Test
  public void scanAllMissedIgnoreFiles() {
    DeepCodeIgnoreInfoHolderBase ignoreInfoHolder = getNewIgnoreInfoHolder();

    ignoreInfoHolder.scanAllMissedIgnoreFiles(Collections.singletonList(basicIgnoreFile), null);
    assertTrue(ignoreInfoHolder.isIgnoredFile(new File(basicProject, "2.js")));
  }

  @NotNull
  private DeepCodeIgnoreInfoHolderBase getNewIgnoreInfoHolder() {
    return new DeepCodeIgnoreInfoHolderBase(
            new MockHashContentUtils(), new MockPlatformDependentUtils(), new MockLogger()) {
    };
  }

  private class MockHashContentUtils extends HashContentUtilsBase {
    protected MockHashContentUtils() {
      super(new MockPlatformDependentUtils());
    }

    @Override
    public @NotNull String doGetFileContent(@NotNull Object file) {
      try {
        return Files.readString(Paths.get(((File)file).getAbsolutePath()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private class MockLogger extends DCLoggerBase {

    protected MockLogger() {
      super(
          () -> System.out::println,
          () -> System.out::println,
          () -> true,
          () -> true,
          "ai.deepcode",
          "");
    }

    @Override
    protected String getExtraInfo() {
      return "";
    }
  }

  private class MockPlatformDependentUtils extends PlatformDependentUtilsBase {
    @Override
    public @NotNull Object getProject(@NotNull Object file) {
      final String filePath = ((File)file).getPath();
      if (filePath.startsWith(basicProject.getPath())) return basicProject;
      if (filePath.startsWith(fullDcignoreProject.getPath())) return fullDcignoreProject;
      throw new IllegalArgumentException(file.toString());
    }

    @Override
    public @NotNull String getProjectName(@NotNull Object project) {
      return project.toString();
    }

    @Override
    public @NotNull String getFileName(@NotNull Object file) {
      return ((File) file).getName();
    }

    @Override
    public @NotNull String getFilePath(@NotNull Object file) {
        return ((File) file).getPath().replaceAll("\\\\", "/"); // case for Windows base path
    }

    @Override
    public @NotNull String getDirPath(@NotNull Object file) {
        return ((File) file).getParent().replaceAll("\\\\", "/"); // case for Windows base path
    }

    // ------------------------------ don't needed below ---------------------------
    @Override
    protected @NotNull String getProjectBasedFilePath(@NotNull Object file) {
      return null;
    }

    @Override
    public Object getFileByDeepcodedPath(String path, Object project) {
      return null;
    }

    @Override
    public Object[] getOpenProjects() {
      return new Object[0];
    }

    @Override
    public long getFileSize(@NotNull Object file) {
      return 0;
    }

    @Override
    public int getLineStartOffset(@NotNull Object file, int line) {
      return 0;
    }

    @Override
    public void runInBackgroundCancellable(
        @NotNull Object file, @NotNull String title, @NotNull Consumer<Object> progressConsumer) {}

    @Override
    public void runInBackground(
        @NotNull Object project,
        @NotNull String title,
        @NotNull Consumer<Object> progressConsumer) {}

    @Override
    public void cancelRunningIndicators(@NotNull Object project) {}

    @Override
    public void doFullRescan(@NotNull Object project) {}

    @Override
    public void refreshPanel(@NotNull Object project) {}

    @Override
    public boolean isLogged(@Nullable Object project, boolean userActionNeeded) {
      return false;
    }

    @Override
    public void progressSetText(@Nullable Object progress, String text) {}

    @Override
    public void progressCheckCanceled(@Nullable Object progress) {}

    @Override
    public boolean progressCanceled(@Nullable Object progress) {
      return false;
    }

    @Override
    public void progressSetFraction(@Nullable Object progress, double fraction) {}

    @Override
    public void showInBrowser(@NotNull String url) {}

    @Override
    public void showLoginLink(@Nullable Object project, String message) {}

    @Override
    public void showConsentRequest(Object project, boolean userActionNeeded) {}

    @Override
    public void showInfo(String message, @Nullable Object project) {}

    @Override
    public void showWarn(String message, @Nullable Object project, boolean wasWarnShown) {}

    @Override
    public void showError(String message, @Nullable Object project) {}
  }
}
