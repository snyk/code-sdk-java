package ai.deepcode.javaclient.responses;

public class AnalysisResults {

  private FilesMap files;
  private Suggestions suggestions;

  public AnalysisResults(FilesMap files, Suggestions suggestions) {
    super();
    this.files = files;
    this.suggestions = suggestions;
  }

  public FilesMap getFiles() {
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