package ai.deepcode.javaclient.core.mocks;

import ai.deepcode.javaclient.core.DeepCodeParamsBase;
import org.jetbrains.annotations.NotNull;

public class DeepCodeParamsMock extends DeepCodeParamsBase {

  public DeepCodeParamsMock() {
    super(true, "", false, false, 1, "", "", "", () -> 1000L);
  }

  @Override
  public boolean consentGiven(@NotNull Object project) {
    return true;
  }

  @Override
  public void setConsentGiven(@NotNull Object project) {}
}
