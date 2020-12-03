package dev.mickael.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.mickael.adventofcode.day3.TobogganMap.Square;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class TobogganMapTest {

  @Test
  void minimalMap() {
    var input =
        new char[][] {
          new char[] {'.', '#'},
          new char[] {'#', '.'}
        };

    var map = TobogganMap.builder().visibleGrid(input).build();

    assertEquals(Square.OPEN, map.squareAt(0, 0));
    assertEquals(Square.TREE, map.squareAt(0, 1));
    assertEquals(Square.TREE, map.squareAt(1, 0));
    assertEquals(Square.OPEN, map.squareAt(1, 1));
  }

  @Test
  void minimalMapInfiniteHorizontalScrolling() {
    var input =
        new char[][] {
          new char[] {'#', '#'},
          new char[] {'.', '.'}
        };

    var map = TobogganMap.builder().visibleGrid(input).build();

    // top row is always a tree,
    // bottom row is always open
    for (int col = 0; col < 10; col++) {
      assertEquals(Square.TREE, map.squareAt(0, col));
      assertEquals(Square.OPEN, map.squareAt(1, col));
    }
  }

  @Test
  void testExampleFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-3-example.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

    var map = TobogganMap.builder().visibleGrid(input).build();

    int row = 0, col = 0, numTrees = 0;
    while (!map.isLastRow(row)) {
      row += 1;
      col += 3;

      if (map.squareAt(row, col) == Square.TREE) {
        numTrees++;
      }
    }

    assertEquals(7, numTrees);
  }

  @Test
  void testInputFileAndCountTreesToBottom() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-3-input.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

    var map = TobogganMap.builder().visibleGrid(input).build();

    int row = 0, col = 0, numTrees = 0;
    while (!map.isLastRow(row)) {
      row += 1;
      col += 3;

      if (map.squareAt(row, col) == Square.TREE) {
        numTrees++;
      }
    }

    assertEquals(row, input.length - 1);
    System.out.println("encountered " + numTrees + " trees on the way down");
  }
}
