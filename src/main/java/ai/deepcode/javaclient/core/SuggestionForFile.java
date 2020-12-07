package ai.deepcode.javaclient.core;

import ai.deepcode.javaclient.responses.ExampleCommitFix;

import java.util.List;

public class SuggestionForFile {
  private final String id;
  private final String rule;
  private final String message;
  private final int severity;
  private int repoDatasetSize;
  private List<ExampleCommitFix> exampleCommitFixes;
  private final List<MyTextRange> ranges;

  public SuggestionForFile(
      String id,
      String rule,
      String message,
      int severity,
      int repoDatasetSize,
      List<ExampleCommitFix> exampleCommitFixes,
      List<MyTextRange> ranges) {
    this.id = id;
    this.rule = rule;
    this.message = message;
    this.severity = severity;
    this.repoDatasetSize = repoDatasetSize;
    this.exampleCommitFixes = exampleCommitFixes;
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

  public int getRepoDatasetSize() {
    return repoDatasetSize;
  }

  public List<ExampleCommitFix> getExampleCommitFixes() {
    return exampleCommitFixes;
  }
}
