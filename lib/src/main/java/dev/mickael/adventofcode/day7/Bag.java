package dev.mickael.adventofcode.day7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bag {

  private final String color;

  @Builder.Default private final Set<String> containedBy = new HashSet<>();

  public boolean isContainedBy(String color) {
    return containedBy.contains(color);
  }

  @Builder.Default private final Map<String, Integer> contains = new HashMap<>();

  public static Bag fromRule(Rule rule) {
    return builder().color(rule.getOuterBagColor()).contains(rule.getInnerBags()).build();
  }
}
