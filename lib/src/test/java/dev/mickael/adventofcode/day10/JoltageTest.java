package dev.mickael.adventofcode.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class JoltageTest {

  private static int[] loadAdapters(String filename) {
    var inputPath = JoltageTest.class.getClassLoader().getResource(filename);
    assertNotNull(inputPath);

    int[] adapters = null;
    try {
      adapters =
          Files.readAllLines(Path.of(inputPath.toURI())).stream()
              .mapToInt(Integer::parseInt)
              .sorted()
              .toArray();
    } catch (IOException | URISyntaxException ignored) {
    }

    assertNotNull(adapters);

    return adapters;
  }

  @Test
  void testJoltageDifferenceWithExample1File() {
    var adapters = loadAdapters("day-10-example-1.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(35, j.joltDifference());
  }

  @Test
  void testJoltageDifferenceWithExample2File() {
    var adapters = loadAdapters("day-10-example-2.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(220, j.joltDifference());
  }

  @Test
  void testJoltageDifference() {
    var adapters = loadAdapters("day-10-input.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(1885, j.joltDifference());
  }

  @Test
  void countArrangementsExample1File() {
    var adapters = loadAdapters("day-10-example-1.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(8, j.arrangements());
  }

  @Test
  void countArrangementsExample2File() {
    var adapters = loadAdapters("day-10-example-2.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(19208, j.arrangements());
  }

  @Test
  void countArrangementsInputFile() {
    var adapters = loadAdapters("day-10-input.txt");
    var j = Joltage.builder().adapters(adapters).build();

    assertEquals(2024782584832L, j.arrangements());
  }
}
