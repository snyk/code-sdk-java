package ai.deepcode.javaclient.responses;

import java.util.List;

public class MarkerPosition implements Position {

  private List<Integer> rows;
  private List<Integer> cols;

  public MarkerPosition(List<Integer> rows, List<Integer> cols) {
    super();
    this.rows = rows;
    this.cols = cols;
  }

  @Override
  public List<Integer> getRows() {
    return rows;
  }

  @Override
  public List<Integer> getCols() {
    return cols;
  }

  @Override
  public String toString() {
    return "marker range:  rows: " + rows + " cols: " + cols;
  }
}
