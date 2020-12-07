package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
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

    var shinyGold = bags.get("shiny gold");
    assertEquals(8, shinyGold.getContainedBy().size());

    var outerBags = shinyGold.getContainedBy();
    var bagsToCheck = new ArrayDeque<>(outerBags);

    while (!bagsToCheck.isEmpty()) {
      var curr = bagsToCheck.removeFirst();

      for (var c : bags.getOrDefault(curr, Bag.builder().build()).getContainedBy()) {
        if (!outerBags.contains(c)) {
          outerBags.add(c);
          bagsToCheck.add(c);
        }
      }
    }

    assertEquals(185, outerBags.size());
  }
}
