package dev.mickael.adventofcode.day6;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomsGroup {

  private Set<Character> yesAnswers;

  public static CustomsGroup fromString(String input) {
    Set<Character> yesAnswers = new HashSet<>();

    for (var c : input.toCharArray()) {
      if (c == '\n') continue;

      yesAnswers.add(c);
    }

    return builder().yesAnswers(yesAnswers).build();
  }
}
