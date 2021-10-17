package ai.deepcode.javaclient.core.mocks;

import ai.deepcode.javaclient.core.PlatformDependentUtilsBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class PlatformDependentUtilsAbstractMock extends PlatformDependentUtilsBase {

  @Override
  public @NotNull Object getProject(@NotNull Object file) {
    return null;
  }

  @Override
  public @NotNull String getProjectName(@NotNull Object project) {
    return null;
  }

  @Override
  public @NotNull String getFileName(@NotNull Object file) {
    return null;
  }

  @Override
  public @NotNull String getFilePath(@NotNull Object file) {
    return null;
  }

  @Override
  public @NotNull String getDirPath(@NotNull Object file) {
    return null;
  }

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
          @NotNull Object file, @NotNull String title, @NotNull Consumer<Object> progressConsumer) {
  }

  @Override
  public void runInBackground(
          @NotNull Object project,
          @NotNull String title,
          @NotNull Consumer<Object> progressConsumer) {
  }

  @Override
  public void cancelRunningIndicators(@NotNull Object project) {
  }

  @Override
  public void doFullRescan(@NotNull Object project) {
  }

  @Override
  public void refreshPanel(@NotNull Object project) {
  }

  @Override
  public boolean isLogged(@Nullable Object project, boolean userActionNeeded) {
    return false;
  }

  @Override
  public void progressSetText(@Nullable Object progress, String text) {
  }

  @Override
  public void progressCheckCanceled(@Nullable Object progress) {
  }

  @Override
  public boolean progressCanceled(@Nullable Object progress) {
    return false;
  }

  @Override
  public void progressSetFraction(@Nullable Object progress, double fraction) {
  }

  @Override
  public void showInBrowser(@NotNull String url) {
  }

  @Override
  public void showLoginLink(@Nullable Object project, String message) {
  }

  @Override
  public void showConsentRequest(Object project, boolean userActionNeeded) {
  }

  @Override
  public void showInfo(String message, @Nullable Object project) {
  }

  @Override
  public void showWarn(String message, @Nullable Object project, boolean wasWarnShown) {
  }

  @Override
  public void showError(String message, @Nullable Object project) {
  }
}
