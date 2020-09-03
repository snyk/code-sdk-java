package ai.deepcode.javaclient.core;

import java.util.List;

public class SuggestionForFile {
  private final String id;
  private final String rule;
  private final String message;
  private final int severity;
  private final List<MyTextRange> ranges;

  public SuggestionForFile(String id, String rule, String message, int severity, List<MyTextRange> ranges) {
    this.id = id;
    this.rule = rule;
    this.message = message;
    this.severity = severity;
    this.ranges = ranges;
  }

  public String getId() {
    return id;
  }

  public String getRule() {
    return rule;
  }

  public String getMessage() {
    return message;
  }

  public List<MyTextRange> getRanges() {
    return ranges;
  }

  public int getSeverity() {
    return severity;
  }
}
