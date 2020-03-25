package ai.deepcode.javaclient.responses;

import java.util.List;

public class MarkerPosition {

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
