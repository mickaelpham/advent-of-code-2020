package dev.mickael.adventofcode.day7;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class RuleMap {

  @Builder.Default private final Map<String, Bag> bags = new HashMap<>();

  public static RuleMap fromRules(List<Rule> rules) {
    // first, create all bags from their rule, and map them by color
    var bags =
        rules.stream()
            .map(Bag::fromRule)
            .collect(Collectors.toMap(Bag::getColor, Function.identity()));

    // then, map each bag to its own possible parents/containers
    rules.forEach(
        rule ->
            rule.getInnerBags().keySet().stream()
                .map(bags::get)
                .map(Bag::getContainedBy)
                .forEach(containedBy -> containedBy.add(rule.getOuterBagColor())));

    return builder().bags(bags).build();
  }

  public Bag get(String color) {
    return bags.get(color);
  }

  public Set<String> outerBagsContaining(String color) {
    var result = new HashSet<String>();

    // Breadth-First Approach
    var bagsToCheck = new ArrayDeque<>(List.of(color));

    while (!bagsToCheck.isEmpty()) {
      var bag = get(bagsToCheck.removeFirst());

      bag.getContainedBy().stream()
          .filter(Predicate.not(result::contains))
          .forEach(
              outerBagColor -> {
                bagsToCheck.add(outerBagColor);
                result.add(outerBagColor);
              });
    }

    return result;
  }

  public int totalInnerBags(String color) {
    int result = 0;

    // Breadth-First Approach
    var bagsToCheck = new ArrayDeque<>(List.of(BagCount.builder().color(color).count(1).build()));

    while (!bagsToCheck.isEmpty()) {
      var curr = bagsToCheck.removeFirst();
      result += curr.count;

      // Add all inner bags to the queue, with their current count
      get(curr.color)
          .getContains()
          .forEach(
              (key, value) ->
                  bagsToCheck.add(BagCount.builder().color(key).count(value * curr.count).build()));
    }

    // We do not count the outer-most bag we started from
    return result - 1;
  }

  @Builder
  private static class BagCount {
    int count;
    String color;
  }
}
