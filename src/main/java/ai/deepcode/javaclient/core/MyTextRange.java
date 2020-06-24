package ai.deepcode.javaclient.core;

public class MyTextRange {
  private final int start;
  private final int end;
  private final int startRow;
  private final int endRow;
  private final int startCol;
  private final int endCol;

  MyTextRange(int start, int end, int startRow, int endRow, int startCol, int endCol) {

    this.start = start;
    this.end = end;
    this.startRow = startRow;
    this.endRow = endRow;
    this.startCol = startCol;
    this.endCol = endCol;
  }

  public int getStart() {
    return start;
  }

  public int getEnd() {
    return end;
  }

  public int getStartRow() {
    return startRow;
  }

  public int getEndRow() {
    return endRow;
  }

  public int getStartCol() {
    return startCol;
  }

  public int getEndCol() {
    return endCol;
  }
}
