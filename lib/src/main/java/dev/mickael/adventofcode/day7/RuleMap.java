package dev.mickael.adventofcode.day7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
}
