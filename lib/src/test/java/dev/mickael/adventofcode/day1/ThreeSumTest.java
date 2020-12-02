package dev.mickael.adventofcode.day1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ThreeSumTest {

  @Test
  void simpleInput() {
    var input = new int[] {1, 2, 3, 4, 5, 6};
    var target = 15;
    var expected = new int[] {4, 6, 5};

    assertArrayEquals(expected, ThreeSum.exec(input, target));
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

    var result = ThreeSum.exec(input, target);
    assertEquals(target, Arrays.stream(result).sum());
    System.out.println(result[0] + " + " + result[1] + " + " + result[2] + " = " + target);
    System.out.println(
        result[0]
            + " * "
            + result[1]
            + " * "
            + result[2]
            + " = "
            + (result[0] * result[1] * result[2]));
  }
}
