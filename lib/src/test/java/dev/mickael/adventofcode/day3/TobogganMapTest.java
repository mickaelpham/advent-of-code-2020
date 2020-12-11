package dev.mickael.adventofcode.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.mickael.adventofcode.day3.TobogganMap.Square;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
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
    var slope = Slope.builder().row(1).col(3).build();

    assertEquals(7, map.treesEncountered(slope));
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
    var slope = Slope.builder().row(1).col(3).build();
    var numTrees = map.treesEncountered(slope);

    assertEquals(228, numTrees);
    log.info("encountered {} trees on the way down", numTrees);
  }

  @Test
  void testMultipleSlopes() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-3-input.txt");
    assertNotNull(inputPath);

    var input =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(String::toCharArray)
            .toArray(char[][]::new);

    var tobogganMap = TobogganMap.builder().visibleGrid(input).build();
    var slopes =
        List.of(
            Slope.builder().row(1).col(1).build(),
            Slope.builder().row(1).col(3).build(),
            Slope.builder().row(1).col(5).build(),
            Slope.builder().row(1).col(7).build(),
            Slope.builder().row(2).col(1).build());

    var numTrees =
        slopes.stream()
            .map(tobogganMap::treesEncountered)
            .map(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);

    assertEquals(BigInteger.valueOf(6818112000L), numTrees);
    log.info("encountered {} trees across all slopes", numTrees);
  }
}
