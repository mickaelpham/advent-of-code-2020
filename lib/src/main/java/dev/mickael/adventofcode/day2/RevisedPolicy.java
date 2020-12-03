package dev.mickael.adventofcode.day2;

import java.util.Arrays;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RevisedPolicy {

  private final int[] positions;
  private final char letter;

  public static RevisedPolicy fromString(String input) {
    var length = input.length();

    // pick up the last character as the letter
    var letter = input.charAt(length - 1);

    // trim the last two characters, then split by `-` to get the positions
    var positions =
        Arrays.stream(input.substring(0, length - 2).split("-"))
            .mapToInt(Integer::parseInt)
            .toArray();
    assert positions.length == 2 : "expected two parsed integers";

    return builder().positions(positions).letter(letter).build();
  }

  public boolean validate(String password) {
    var count = 0;

    for (var p : positions) {
      // sanity check
      if (password.length() < p) {
        return false;
      }

      if (password.charAt(p - 1) == letter) {
        count++;
      }
    }

    return count == 1;
  }
}
