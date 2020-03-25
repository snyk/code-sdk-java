package ai.deepcode.javaclient.responses;

import java.util.List;

public class FileRange {

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
