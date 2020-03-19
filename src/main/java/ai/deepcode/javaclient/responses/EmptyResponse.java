package ai.deepcode.javaclient.responses;

public class EmptyResponse {
  private int statusCode = 0;

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
