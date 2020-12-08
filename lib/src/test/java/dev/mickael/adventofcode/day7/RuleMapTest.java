package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RuleMapTest {

  private static final String EXAMPLE_FILENAME = "day-7-example.txt";
  private static final String INPUT_FILENAME = "day-7-input.txt";
  private static final String SHINY_GOLD = "shiny gold";

  private static RuleMap loadRules(String filename) {
    var inputPath = RuleMapTest.class.getClassLoader().getResource(filename);
    assertNotNull(inputPath, "could not load resource: " + filename);

    List<Rule> rules = null;
    try {
      rules =
          Files.readAllLines(Path.of(inputPath.toURI())).stream()
              .map(Rule::fromString)
              .collect(Collectors.toList());
    } catch (IOException | URISyntaxException ignored) {
    }
    assertNotNull(rules, "could not load parse rules file");

    return RuleMap.fromRules(rules);
  }

  @Test
  void testWithExampleFile() {
    var rules = loadRules(EXAMPLE_FILENAME);
    assertTrue(rules.get("dark blue").isContainedBy("dark green"));
  }

  @Test
  void testOuterBagsFromInputFile() {
    var rules = loadRules(INPUT_FILENAME);
    assertEquals(185, rules.outerBagsContaining(SHINY_GOLD).size());
  }

  @Test
  void testInnerBagsWithExampleFile() {
    var rules = loadRules(EXAMPLE_FILENAME);
    assertEquals(126, rules.totalInnerBags(SHINY_GOLD));
  }

  @Test
  void testInnerBagsWithInputFile() {
    var rules = loadRules(INPUT_FILENAME);
    assertEquals(89084, rules.totalInnerBags(SHINY_GOLD));
  }
}
