package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class BagTest {

  @Test
  void testFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-7-input.txt");
    assertNotNull(inputPath);

    var rules =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Rule::fromString)
            .collect(Collectors.toList());

    var bags = new HashMap<String, Bag>();

    for (var r : rules) {
      for (var innerColor : r.getInnerBags().keySet()) {
        var innerBag = bags.getOrDefault(innerColor, Bag.builder().color(innerColor).build());
        innerBag.getContainedBy().add(r.getOuterBagColor());

        bags.put(innerBag.getColor(), innerBag);
      }
    }

    assertEquals(8, bags.get("shiny gold").getContainedBy().size());
  }
}
