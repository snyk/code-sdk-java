package ai.deepcode.javaclient.responses;

public class AnalysisResults {

  private Files files;
  private Suggestions suggestions;

  public AnalysisResults(Files files, Suggestions suggestions) {
    super();
    this.files = files;
    this.suggestions = suggestions;
  }

  public Files getFiles() {
    return files;
  }

  public Suggestions getSuggestions() {
    return suggestions;
  }

  @Override
  public String toString() {
    return "AnalysisResults object:\n" + files + "\n" + suggestions;
  }
}