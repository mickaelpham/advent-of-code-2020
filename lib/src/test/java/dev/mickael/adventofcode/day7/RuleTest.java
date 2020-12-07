package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RuleTest {

  @Test
  void testParsingOneRule() {
    var rule = Rule.fromString("light green bags contain 4 drab magenta bags, 3 dark orange bags.");

    assertEquals("light green", rule.getOuterBagColor());
    assertEquals(4, rule.getInnerBags().get("drab magenta"));
    assertEquals(3, rule.getInnerBags().get("dark orange"));
  }
}
