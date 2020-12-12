package dev.mickael.adventofcode.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class ShipWaypointTest {

  @Test
  void testExampleFile() throws URISyntaxException, IOException {
    var ship = ShipWaypoint.builder().build();

    var inputPath = getClass().getClassLoader().getResource("day-12-example.txt");
    assertNotNull(inputPath);

    Files.readAllLines(Path.of(inputPath.toURI())).forEach(ship::exec);
    assertEquals(286, ship.manhattanDistance());
  }

  @Test
  void testInputFile() throws URISyntaxException, IOException {
    var ship = ShipWaypoint.builder().build();

    var inputPath = getClass().getClassLoader().getResource("day-12-input.txt");
    assertNotNull(inputPath);

    Files.readAllLines(Path.of(inputPath.toURI())).forEach(ship::exec);
    assertEquals(58606, ship.manhattanDistance());
  }
}
