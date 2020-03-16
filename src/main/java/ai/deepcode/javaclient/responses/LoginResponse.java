package ai.deepcode.javaclient.responses;

public class LoginResponse {
  public final String sessionToken;
  public final String loginURL;

  private LoginResponse(String sessionToken, String loginURL) {
    this.sessionToken = sessionToken;
    this.loginURL = loginURL;
  }
}