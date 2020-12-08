package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RuleMapTest {

  @Test
  void testWithExampleInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-7-example.txt");
    assertNotNull(inputPath);

    var rules =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Rule::fromString)
            .collect(Collectors.toList());

    var map = RuleMap.fromRules(rules);
    assertTrue(map.get("dark blue").isContainedBy("dark green"));
  }

  @Test
  void testOuterBagsFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-7-input.txt");
    assertNotNull(inputPath);

    var rules =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Rule::fromString)
            .collect(Collectors.toList());

    var map = RuleMap.fromRules(rules);
    assertEquals(185, map.outerBagsContaining("shiny gold").size());
  }
}
