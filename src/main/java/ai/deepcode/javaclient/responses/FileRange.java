package ai.deepcode.javaclient.responses;

import java.util.List;

public class FileRange implements Position {

  private List<Integer> rows = null;
  private List<Integer> cols = null;
  private List<Marker> markers = null;

  public FileRange(List<Integer> rows, List<Integer> cols, List<Marker> markers) {
    super();
    this.rows = rows;
    this.cols = cols;
    this.markers = markers;
  }

  @Override
  public List<Integer> getRows() {
    return rows;
  }

  @Override
  public List<Integer> getCols() {
    return cols;
  }

  public List<Marker> getMarkers() {
    return markers;
  }

  @Override
  public String toString() {
    return super.toString() + " markers: " + markers;
  }
}
