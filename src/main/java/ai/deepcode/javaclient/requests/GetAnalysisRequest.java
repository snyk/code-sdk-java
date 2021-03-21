package ai.deepcode.javaclient.requests;

import java.util.List;

public class GetAnalysisRequest {
  private List<String> files;

  /**
   * @param files List of FilePaths
   */
  public GetAnalysisRequest(List<String> files) {
    super();
    this.files = files;
  }

  public List<String> getFiles() {
    return files;
  }

}
