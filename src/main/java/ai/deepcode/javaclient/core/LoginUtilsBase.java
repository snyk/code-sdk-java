package ai.deepcode.javaclient.core;

import ai.deepcode.javaclient.DeepCodeRestApi;
import ai.deepcode.javaclient.responses.EmptyResponse;
import ai.deepcode.javaclient.responses.LoginResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public abstract class LoginUtilsBase {

  private final PlatformDependentUtilsBase pdUtils;
  private final DeepCodeParamsBase deepCodeParams;
  private final AnalysisDataBase analysisData;
  private final DCLoggerBase dcLogger;

  protected LoginUtilsBase(
      @NotNull PlatformDependentUtilsBase platformDependentUtils,
      @NotNull DeepCodeParamsBase deepCodeParams,
      @NotNull AnalysisDataBase analysisData,
      @NotNull DCLoggerBase dcLogger) {
    this.pdUtils = platformDependentUtils;
    this.deepCodeParams = deepCodeParams;
    this.analysisData = analysisData;
    this.dcLogger = dcLogger;
  }

  protected abstract String getUserAgent();

  public boolean checkConsent(@NotNull Object project, boolean userActionNeeded) {
    final boolean consentGiven = deepCodeParams.consentGiven(project);
    if (consentGiven) {
      dcLogger.logInfo("Consent check succeed for: " + pdUtils.getProjectName(project));
    } else {
      dcLogger.logWarn("Consent check fail! Project: " + pdUtils.getProjectName(project));
      if (userActionNeeded) {
        pdUtils.showConsentRequest(project, true);
      }
    }
    return consentGiven;
  }
}
