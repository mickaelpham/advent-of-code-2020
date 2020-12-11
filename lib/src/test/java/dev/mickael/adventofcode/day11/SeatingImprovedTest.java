package dev.mickael.adventofcode.day11;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.mickael.adventofcode.day11.SeatingImproved.Seat;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class SeatingImprovedTest {

  @Test
  void testParser() {
    var input =
        new char[][] {
          new char[] {'.', 'L'},
          new char[] {'#', '.'}
        };

    var expected =
        new Seat[][] {
          new Seat[] {Seat.GROUND, Seat.EMPTY},
          new Seat[] {Seat.OCCUPIED, Seat.GROUND}
        };

    var seating = SeatingImproved.fromInput(input).getGrid();

    assertArrayEquals(expected, seating);
  }

  @Test
  void testExampleFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-11-example.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(String::toCharArray)
            .collect(Collectors.toList());

    // FIXME find a better way to convert this list to an array
    char[][] grid = new char[input.size()][input.get(0).length];
    for (int i = 0; i < input.size(); i++) {
      grid[i] = input.get(i);
    }

    var seating = SeatingImproved.fromInput(grid);
    while (seating.next())
      ;

    assertEquals(26, seating.numEmpty());
  }

  @Test
  void testInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-11-input.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(String::toCharArray)
            .collect(Collectors.toList());

    // FIXME find a better way to convert this list to an array
    char[][] grid = new char[input.size()][input.get(0).length];
    for (int i = 0; i < input.size(); i++) {
      grid[i] = input.get(i);
    }

    var seating = SeatingImproved.fromInput(grid);
    while (seating.next())
      ;

    assertEquals(2117, seating.numEmpty());
  }
}
