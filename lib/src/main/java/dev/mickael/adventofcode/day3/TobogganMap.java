package dev.mickael.adventofcode.day3;

import java.util.Map;
import lombok.Builder;

@Builder
public class TobogganMap {

  private static final Map<Character, Square> MARKS =
      Map.of(
          '#', Square.TREE,
          '.', Square.OPEN);

  private final char[][] visibleGrid;

  /**
   * Used to indicate that we reached the bottom of the map
   *
   * @param row Current row position
   * @return true if we reached the end, false otherwise
   */
  public boolean isLastRow(int row) {
    return (visibleGrid.length - 1) == row;
  }

  /**
   * Indicates what's on the grid at the given position (row, col). The `row` must be within the
   * grid length, but the `col` can go over, since the grid repeats itself horizontally.
   *
   * @param row Row position
   * @param col Column position
   * @return a Square value
   */
  public Square squareAt(int row, int col) {
    int visibleCol = col % visibleGrid[0].length;

    return MARKS.get(visibleGrid[row][visibleCol]);
  }

  public enum Square {
    TREE,
    OPEN
  }
}
