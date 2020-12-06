package dev.mickael.adventofcode.day6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RevisedCustomsGroup {

  private Set<Character> yesAnswers;

  public static RevisedCustomsGroup fromString(String input) {
    Map<Character, Integer> frequencyMap = new HashMap<>();
    Set<Character> yesAnswers = new HashSet<>();
    int numPeople = 0;

    for (var c : input.toCharArray()) {
      if (c == '\n') {
        numPeople++;
        continue;
      }

      frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
    }

    for (var entry : frequencyMap.entrySet()) {
      if (entry.getValue() == numPeople) {
        yesAnswers.add(entry.getKey());
      }
    }

    return builder().yesAnswers(yesAnswers).build();
  }
}
