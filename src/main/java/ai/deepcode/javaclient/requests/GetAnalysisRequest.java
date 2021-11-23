package ai.deepcode.javaclient.requests;

import java.util.List;
import java.util.Map;

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
  }

  public GetAnalysisRequest(String bundleHash, List<String>limitToFiles, Integer severity) {
    this(bundleHash, limitToFiles, severity, false, true);
  }

  public GetAnalysisRequest(String bundleHash, List<String>limitToFiles) {
    this(bundleHash, limitToFiles, 2, false, true);
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
  }
}
