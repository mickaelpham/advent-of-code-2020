package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class BagTest {

  @Test
  void testBuildFromRule() {
    Map<String, Integer> innerBags = Map.of("muted lavender", 2);
    var rule = Rule.builder().outerBagColor("dark green").innerBags(innerBags).build();
    var bag = Bag.fromRule(rule);

    assertEquals(innerBags, bag.getContains());
    assertEquals("dark green", bag.getColor());
  }
}
