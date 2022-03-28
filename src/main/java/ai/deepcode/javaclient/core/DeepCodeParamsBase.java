package ai.deepcode.javaclient.core;

import ai.deepcode.javaclient.DeepCodeRestApi;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class DeepCodeParamsBase {

  // Settings
  private boolean isEnable;
  private String apiUrl;
  private boolean disableSslVerification;
  private boolean useLinter;
  private int minSeverity;
  private String sessionToken;

  // Inner params
  private String loginUrl;
  private String ideProductName;
  private String orgDisplayName;
  private Supplier<Long> getTimeoutForGettingAnalysesMs;
  private final DeepCodeRestApi restApi;

  protected DeepCodeParamsBase(
    boolean isEnable,
    String apiUrl,
    boolean disableSslVerification,
    boolean useLinter,
    int minSeverity,
    String sessionToken,
    String loginUrl,
    String ideProductName,
    String orgDisplayName,
    Supplier<Long> getTimeoutForGettingAnalysesMs,
    DeepCodeRestApi restApi
  ) {
    this.isEnable = isEnable;
    this.apiUrl = apiUrl;
    this.disableSslVerification = disableSslVerification;
    this.useLinter = useLinter;
    this.minSeverity = minSeverity;
    this.sessionToken = sessionToken;
    this.loginUrl = loginUrl;
    this.ideProductName = ideProductName;
    this.orgDisplayName = orgDisplayName;
    this.getTimeoutForGettingAnalysesMs = getTimeoutForGettingAnalysesMs;
    this.restApi = restApi;
  }

  public void clearLoginParams() {
    setSessionToken("");
    setLoginUrl("");
  }

  @NotNull
  public String getSessionToken() {
    return sessionToken;
  }

  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }

  @NotNull
  public String getLoginUrl() {
    return loginUrl;
  }

  public void setLoginUrl(String loginUrl) {
    this.loginUrl = loginUrl;
  }

  public boolean useLinter() {
    return useLinter;
  }

  public void setUseLinter(boolean useLinter) {
    this.useLinter = useLinter;
  }

  public int getMinSeverity() {
    return minSeverity;
  }

  public void setMinSeverity(int minSeverity) {
    this.minSeverity = minSeverity;
  }

  @NotNull
  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(@NotNull String apiUrl) {
    setApiUrl(apiUrl, false);
  }

  public void setApiUrl(@NotNull String apiUrl, boolean disableSslVerification) {
    setApiUrl(apiUrl, disableSslVerification, false);
  }

  public void setApiUrl(
      @NotNull String apiUrl, boolean disableSslVerification, boolean requestLogging) {
    if (apiUrl.isEmpty()) apiUrl = "https://deeproxy.snyk.io/";
    if (!apiUrl.endsWith("/")) apiUrl += "/";
    if (apiUrl.equals(this.apiUrl)) return;
    this.apiUrl = apiUrl;
    this.disableSslVerification = disableSslVerification;
    restApi.setBaseUrl(apiUrl, disableSslVerification, requestLogging);
  }

  public boolean isDisableSslVerification() {
    return disableSslVerification;
  }

  public void setDisableSslVerification(boolean disableSslVerification) {
    this.disableSslVerification = disableSslVerification;
  }

  public boolean isEnable() {
    return isEnable;
  }

  public void setEnable(boolean isEnable) {
    this.isEnable = isEnable;
  }

  public abstract boolean consentGiven(@NotNull Object project);

  public abstract void setConsentGiven(@NotNull Object project);

  public String getIdeProductName() {
    return ideProductName;
  }

  public long getTimeoutForGettingAnalysesMs() {
    return getTimeoutForGettingAnalysesMs.get();
  }

  public String getOrgDisplayName() {
    return orgDisplayName;
  }
}
