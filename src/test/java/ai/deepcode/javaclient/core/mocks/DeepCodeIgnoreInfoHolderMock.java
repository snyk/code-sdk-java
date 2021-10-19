package ai.deepcode.javaclient.core.mocks;

import ai.deepcode.javaclient.core.*;
import org.jetbrains.annotations.NotNull;

public class DeepCodeIgnoreInfoHolderMock extends DeepCodeIgnoreInfoHolderBase {

  public DeepCodeIgnoreInfoHolderMock(
          @NotNull HashContentUtilsBase hashContentUtils,
          @NotNull PlatformDependentUtilsBase pdUtils,
          @NotNull DCLoggerBase dcLogger) {
    super(hashContentUtils, pdUtils, dcLogger);
  }
}
