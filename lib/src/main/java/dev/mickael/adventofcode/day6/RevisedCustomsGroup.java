package dev.mickael.adventofcode.day6;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RevisedCustomsGroup {

  private Set<Character> yesAnswers;

  public static RevisedCustomsGroup fromString(String input) {
    var frequencyMap =
        input
            .chars()
            .mapToObj(chr -> (char) chr)
            .collect(
                Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));

    // Variable used in lambda should be final or effectively final
    final var numPeople = frequencyMap.remove('\n');

    var yesAnswers =
        frequencyMap.entrySet().stream()
            .filter(entry -> entry.getValue().equals(numPeople))
            .map(Entry::getKey)
            .collect(Collectors.toSet());

    return builder().yesAnswers(yesAnswers).build();
  }
}
