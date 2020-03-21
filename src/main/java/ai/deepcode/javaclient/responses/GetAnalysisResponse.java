package ai.deepcode.javaclient.responses;

public class GetAnalysisResponse extends EmptyResponse {
  private final String status;
  private final int progress;
  private final String analysisURL;
  private final AnalysisResults analysisResults;
//  private final JsonElement analysisResults;

  public GetAnalysisResponse(){
    super();
    status = "";
    progress = 0;
    analysisURL = "";
    analysisResults = null;
  }

  public GetAnalysisResponse(
      String status, int progress, String analysisURL, AnalysisResults analysisResults) {
    this.status = status;
    this.progress = progress;
    this.analysisURL = analysisURL;
    this.analysisResults = analysisResults;
  }

  public String getStatus() {
    return status;
  }

  public int getProgress() {
    return progress;
  }

  public String getAnalysisURL() {
    return analysisURL;
  }

  public AnalysisResults getAnalysisResults() {
    return analysisResults;
  }

  @Override
  public String toString() {
    return "GetAnalysisResponse object:"
        + "\nstatus: "
        + status
        + "\nprogress: "
        + progress
        + "\nanalysisURL: "
        + analysisURL
        + "\nanalysisResult: "
        + analysisResults;
  }
}
