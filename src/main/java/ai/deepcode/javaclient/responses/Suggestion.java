package ai.deepcode.javaclient.responses;

import java.util.List;

public class Suggestion {

  private String id;
  private String rule;
  private String message;
  private int severity;
  private int repoDatasetSize;
  private List<ExampleCommitFix> exampleCommitFixes;

  public Suggestion(String id, String rule, String message, int severity, int repoDatasetSize, List<ExampleCommitFix> exampleCommitFixes) {
    super();
    this.id = id;
    this.rule = rule;
    this.message = message;
    this.severity = severity;
    this.repoDatasetSize = repoDatasetSize;
    this.exampleCommitFixes = exampleCommitFixes;
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
    return " id: " + id + " rule: " + rule + " message: " + message + " severity: " + severity
            + " repoDatasetSize: " + repoDatasetSize+ " exampleCommitFixes.size: " + exampleCommitFixes.size();
  }

  public String getRule() {
    return rule;
  }

  public int getRepoDatasetSize() {
    return repoDatasetSize;
  }

  public List<ExampleCommitFix> getExampleCommitFixes() {
    return exampleCommitFixes;
  }
}
