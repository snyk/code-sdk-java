package ai.deepcode.javaclient.responses;

import java.util.HashMap;
import java.util.List;

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

// --------------------- "files" section ----------------------
class Files extends HashMap<String, FileSuggestions> {}

class FileSuggestions extends HashMap<String, List<FileRange>> {}

class FileRange {

  private List<Integer> rows = null;
  private List<Integer> cols = null;
  private List<Marker> markers = null;

  public FileRange(List<Integer> rows, List<Integer> cols, List<Marker> markers) {
    super();
    this.rows = rows;
    this.cols = cols;
    this.markers = markers;
  }

  public List<Integer> getRows() {
    return rows;
  }

  public List<Integer> getCols() {
    return cols;
  }

  public List<Marker> getMarkers() {
    return markers;
  }

  @Override
  public String toString() {
    return " rows: " + rows + " cols: " + cols + " markers: " + markers;
  }
}

class Marker {

  private List<Integer> msg = null;
  private List<MarkerPosition> markerPositions = null;

  public Marker(List<Integer> msg, List<MarkerPosition> markerPositions) {
    super();
    this.msg = msg;
    this.markerPositions = markerPositions;
  }

  public List<Integer> getMsg() {
    return msg;
  }

  public List<MarkerPosition> getPos() {
    return markerPositions;
  }

  @Override
  public String toString() {
    return " msg: " + msg + " pos: " + markerPositions;
  }
}

class MarkerPosition {

  private List<Integer> rows = null;
  private List<Integer> cols = null;

  public MarkerPosition(List<Integer> rows, List<Integer> cols) {
    super();
    this.rows = rows;
    this.cols = cols;
  }

  public List<Integer> getRows() {
    return rows;
  }

  public List<Integer> getCols() {
    return cols;
  }

  @Override
  public String toString() {
    return " rows: " + rows + " cols: " + cols;
  }
}

// ------------------------------ "suggestions" section ----------------

class Suggestions extends HashMap<String, Suggestion> {}

class Suggestion {

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
