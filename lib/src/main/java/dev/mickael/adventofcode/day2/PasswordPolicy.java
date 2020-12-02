package dev.mickael.adventofcode.day2;

import java.util.Arrays;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PasswordPolicy {

  private final int minCount;
  private final int maxCount;
  private final char letter;

  public static PasswordPolicy fromString(String input) {
    var length = input.length();

    // pick up the last character as the letter
    var letter = input.charAt(length - 1);

    // trim the last two characters, then split by `-` to get the min/max count
    var limits =
        Arrays.stream(input.substring(0, length - 2).split("-"))
            .mapToInt(Integer::parseInt)
            .toArray();
    assert limits.length == 2 : "expected two parsed integers";

    return builder().minCount(limits[0]).maxCount(limits[1]).letter(letter).build();
  }

  public boolean validate(String password) {
    var frequency = new int['z' - 'a' + 1];
    password.chars().forEach(n -> frequency[n - 'a']++);

    var count = frequency[letter - 'a'];
    return minCount <= count && maxCount >= count;
  }
}
