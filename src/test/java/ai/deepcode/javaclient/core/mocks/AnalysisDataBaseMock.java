package ai.deepcode.javaclient.core.mocks;

import ai.deepcode.javaclient.core.AnalysisDataBase;
import ai.deepcode.javaclient.core.DCLoggerBase;
import ai.deepcode.javaclient.core.DeepCodeParamsBase;
import ai.deepcode.javaclient.core.HashContentUtilsBase;
import ai.deepcode.javaclient.core.PlatformDependentUtilsBase;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class AnalysisDataBaseMock extends AnalysisDataBase {

  public AnalysisDataBaseMock(
      @NotNull PlatformDependentUtilsBase platformDependentUtils,
      @NotNull HashContentUtilsBase hashContentUtils,
      @NotNull DeepCodeParamsBase deepCodeParams,
      @NotNull DCLoggerBase dcLogger) {
    super(platformDependentUtils, hashContentUtils, deepCodeParams, dcLogger);
  }

  @Override
  protected void updateUIonFilesRemovalFromCache(@NotNull Collection<Object> files) {}
}
