package ai.deepcode.javaclient.requests;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetAnalysisRequest {
  private GetAnalysisKey key;
  private Integer severity;
  private boolean prioritized = false;
  private boolean legacy = true;

  /**
   *
   * @param bundleHash
   * @param limitToFiles
   * @param severity
   * @param prioritized
   * @param legacy
   */
  public GetAnalysisRequest(String bundleHash, List<String>limitToFiles, Integer severity, boolean prioritized, boolean legacy) {
    this.key = new GetAnalysisKey(bundleHash, limitToFiles);
    this.severity = severity;
    this.prioritized = prioritized;
    this.legacy = legacy;
  }

  public GetAnalysisRequest(String bundleHash, List<String>limitToFiles, Integer severity) {
    this(bundleHash, limitToFiles, severity, false, true);
  }

  public GetAnalysisRequest(String bundleHash, List<String>limitToFiles) {
    this(bundleHash, limitToFiles, 0, false, true);
  }



  private class GetAnalysisKey {
    private String type = "file";
    private String hash;
    private List<String> limitToFiles;

    public GetAnalysisKey(String hash, List<String> limitToFiles) {
      this.type = type;
      this.hash = hash;
      this.limitToFiles = limitToFiles;
    }

    public String getHash() {
      return hash;
    }

    public List<String> getLimitToFiles() {
      return limitToFiles;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      GetAnalysisKey that = (GetAnalysisKey) o;
      return type.equals(that.type) && hash.equals(that.hash) && Objects.equals(limitToFiles, that.limitToFiles);
    }

    @Override
    public String toString() {
      return "GetAnalysisKey{" +
              "type='" + type + '\'' +
              ", hash='" + hash + '\'' +
              ", limitToFiles=" + limitToFiles +
              '}';
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, hash, limitToFiles);
    }
  }
}
