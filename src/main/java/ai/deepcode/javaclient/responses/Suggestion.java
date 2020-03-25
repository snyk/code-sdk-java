package ai.deepcode.javaclient.responses;

public class Suggestion {

  private String id;
  private String message;
  private int severity;

  public Suggestion(String id, String message, int severity) {
    super();
    this.id = id;
    this.message = message;
    this.severity = severity;
  }

  public String getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public int getSeverity() {
    return severity;
  }

  @Override
  public String toString() {
    return " id: " + id + " message: " + message + " severity: " + severity;
  }

}
