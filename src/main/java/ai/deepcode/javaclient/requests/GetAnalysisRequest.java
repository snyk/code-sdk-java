package ai.deepcode.javaclient.requests;

import java.util.List;

public class GetAnalysisRequest {
  private GetAnalysisRequestKey key;
  private Integer severity = 1;
  private boolean legacy = true; // SARIF is non legacy

  public GetAnalysisRequest(GetAnalysisRequestKey key, Integer severity, boolean legacy) {
    this.key = key;
    this.severity = severity;
    this.legacy = legacy;
  }

  public GetAnalysisRequestKey getKey() {
    return key;
  }

  public void setKey(GetAnalysisRequestKey key) {
    this.key = key;
  }

  public int getSeverity() {
    return severity;
  }

  public void setSeverity(int severity) {
    this.severity = severity;
  }

  public boolean isLegacy() {
    return legacy;
  }

  public void setLegacy(boolean legacy) {
    this.legacy = legacy;
  }
}
