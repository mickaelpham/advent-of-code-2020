package dev.mickael.adventofcode.day6;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomsGroup {

  private Set<Character> yesAnswers;

  public static CustomsGroup fromString(String input) {
    var yesAnswers =
        input
            .chars()
            .mapToObj(chr -> (char) chr)
            .filter(chr -> chr != '\n')
            .collect(Collectors.toSet());

    return builder().yesAnswers(yesAnswers).build();
  }
}
