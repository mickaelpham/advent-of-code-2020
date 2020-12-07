package dev.mickael.adventofcode.day7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
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

  @Test
  void testShinyBagContainsWithExampleInput() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-7-example.txt");
    assertNotNull(inputPath);

    var rules =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Rule::fromString)
            .collect(Collectors.toList());

    var bags = new HashMap<String, Bag>();

    for (var r : rules) {
      var outerBag =
          bags.getOrDefault(
              r.getOuterBagColor(), Bag.builder().color(r.getOuterBagColor()).build());
      bags.put(outerBag.getColor(), outerBag);

      for (var entry : r.getInnerBags().entrySet()) {
        var innerColor = entry.getKey();
        var innerBag = bags.getOrDefault(innerColor, Bag.builder().color(innerColor).build());
        bags.put(innerBag.getColor(), innerBag);

        innerBag.getContainedBy().add(r.getOuterBagColor());
        outerBag
            .getContains()
            .put(
                innerBag.getColor(),
                outerBag.getContains().getOrDefault(innerBag.getColor(), 0) + entry.getValue());
      }
    }

    var shinyGold = bags.get("shiny gold");
    assertEquals(1, shinyGold.getContains().size());

    int totalBags = 0;

    var innerBags = new ArrayDeque<>(new ArrayList<>(shinyGold.getContains().entrySet()));
    while (!innerBags.isEmpty()) {
      var entry = innerBags.removeFirst();

      totalBags += entry.getValue();

      var bag = bags.get(entry.getKey());
      var entriesToAdd = bag.getContains().entrySet();
      for (var e : entriesToAdd) {
        e.setValue(entry.getValue() * e.getValue());
      }

      innerBags.addAll(new ArrayList<>(entriesToAdd));
    }

    assertEquals(126, totalBags);
  }

  @Test
  void testShinyBagContainsMultipleInnerBags() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-7-input.txt");
    assertNotNull(inputPath);

    var rules =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Rule::fromString)
            .collect(Collectors.toList());

    var bags = new HashMap<String, Bag>();

    for (var r : rules) {
      var outerBag =
          bags.getOrDefault(
              r.getOuterBagColor(), Bag.builder().color(r.getOuterBagColor()).build());
      bags.put(outerBag.getColor(), outerBag);

      for (var entry : r.getInnerBags().entrySet()) {
        var innerColor = entry.getKey();
        var innerBag = bags.getOrDefault(innerColor, Bag.builder().color(innerColor).build());
        bags.put(innerBag.getColor(), innerBag);

        innerBag.getContainedBy().add(r.getOuterBagColor());
        outerBag
            .getContains()
            .put(
                innerBag.getColor(),
                outerBag.getContains().getOrDefault(innerBag.getColor(), 0) + entry.getValue());
      }
    }

    var shinyGold = bags.get("shiny gold");
    assertEquals(4, shinyGold.getContains().size());

    BigInteger totalBags = new BigInteger("0");
    var entriesToAdd = new HashMap<String, BigInteger>();
    for (var e : shinyGold.getContains().entrySet()) {
      entriesToAdd.put(e.getKey(), BigInteger.valueOf(e.getValue()));
    }

    var innerBags = new ArrayDeque<>(new ArrayList<>(entriesToAdd.entrySet()));
    while (!innerBags.isEmpty()) {
      var entry = innerBags.removeFirst();

      totalBags = totalBags.add(entry.getValue());

      var bag = bags.get(entry.getKey());
      var currentEntriesToAdd = new HashMap<String, BigInteger>();
      for (var e : bag.getContains().entrySet()) {
        currentEntriesToAdd.put(
            e.getKey(), BigInteger.valueOf(e.getValue()).multiply(entry.getValue()));
      }

      innerBags.addAll(new ArrayList<>(currentEntriesToAdd.entrySet()));
    }

    assertEquals(BigInteger.valueOf(89084), totalBags);
  }
}
