package dev.mickael.adventofcode.day5;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Seat {

  private static final Map<Character, Search> HALF =
      Map.of(
          'F', Search.LOWER,
          'B', Search.UPPER,
          'R', Search.UPPER,
          'L', Search.LOWER);

  private static final int ENCODED_COLUMN_START = 7;

  private static final int MAX_PLANE_COLUMNS = 8;

  @Getter(lazy = true)
  private final int row = decodeRow();

  @Getter(lazy = true)
  private final int column = decodeColumn();

  @Getter(lazy = true)
  private final int ID = computeID();

  private final String encodedRow;

  private final String encodedColumn;

  /**
   * Decode a string input representing a column or row in the plane
   *
   * @param input Sequence of either F(ront)/B(ack) or L(eft)/(R)right characters
   * @return decoded value as an integer
   */
  protected static int decode(String input) {
    int lo = 0;
    int hi = (1 << input.length()) - 1;

    for (char c : input.toCharArray()) {
      if (!HALF.containsKey(c))
        throw new IllegalArgumentException(
            "could not decode character " + c + " with input " + input);

      int mid = lo + (hi - lo) / 2;

      switch (HALF.get(c)) {
        case UPPER:
          lo = mid + 1;
          break;
        case LOWER:
          hi = mid;
          break;
      }
    }

    return Math.min(lo, hi);
  }

  public static Seat fromString(String input) {
    var encodedRow = input.substring(0, ENCODED_COLUMN_START);
    var encodedCol = input.substring(ENCODED_COLUMN_START, input.length());

    return builder().encodedRow(encodedRow).encodedColumn(encodedCol).build();
  }

  private int decodeColumn() {
    assert encodedColumn != null;

    return decode(encodedColumn);
  }

  private int decodeRow() {
    assert encodedRow != null;

    return decode(encodedRow);
  }

  private int computeID() {
    return getRow() * MAX_PLANE_COLUMNS + getColumn();
  }

  private enum Search {
    LOWER,
    UPPER
  }
}
