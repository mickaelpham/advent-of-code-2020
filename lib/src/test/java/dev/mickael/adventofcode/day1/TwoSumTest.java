package dev.mickael.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class TwoSumTest {

  @Test
  void simpleInput() {
    int[] input = new int[] {1, 2, 3, 4, 5};
    int target = 8;
    int[] expected = new int[] {5, 3};

    assertArrayEquals(expected, TwoSum.find(input, target));
  }

  @Test
  void txtFileInputFromSite() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-1-input.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .toArray();

    int target = 2020;

    var result = TwoSum.find(input, target);
    assertEquals(target, (result[0] + result[1]));
    System.out.println(result[0] + " + " + result[1] + " = " + target);
    System.out.println(result[0] + " * " + result[1] + " = " + (result[0] * result[1]));
  }
}
