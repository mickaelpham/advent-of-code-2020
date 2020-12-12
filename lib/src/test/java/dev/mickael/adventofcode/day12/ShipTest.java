package dev.mickael.adventofcode.day12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.mickael.adventofcode.day12.Ship.Direction;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class ShipTest {

  @Test
  void shipInitiallyFacesEast() {
    var ship = Ship.builder().build();

    assertEquals(Direction.EAST, ship.getFacing());
  }

  @Test
  void testRotatingShipRight() {
    var ship = Ship.builder().build();
    ship.exec("R90");

    assertEquals(Direction.SOUTH, ship.getFacing());
  }

  @Test
  void testRotatingShipLeft() {
    var ship = Ship.builder().build();
    ship.exec("L180");

    assertEquals(Direction.WEST, ship.getFacing());
  }

  @Test
  void moveShipForward() {
    var ship = Ship.builder().build();
    ship.exec("F5");

    assertEquals(5, ship.manhattanDistance());
  }

  @Test
  void rotateShipAndMoveForward() {
    var ship = Ship.builder().build();
    ship.exec("L180");
    ship.exec("F2");

    assertEquals(2, ship.manhattanDistance());
  }

  @Test
  void testExampleFile() throws URISyntaxException, IOException {
    var ship = Ship.builder().build();

    var inputPath = getClass().getClassLoader().getResource("day-12-example.txt");
    assertNotNull(inputPath);

    Files.readAllLines(Path.of(inputPath.toURI())).forEach(ship::exec);
    assertEquals(25, ship.manhattanDistance());
  }

  @Test
  void testInputFile() throws URISyntaxException, IOException {
    var ship = Ship.builder().build();

    var inputPath = getClass().getClassLoader().getResource("day-12-input.txt");
    assertNotNull(inputPath);

    Files.readAllLines(Path.of(inputPath.toURI())).forEach(ship::exec);
    assertEquals(1631, ship.manhattanDistance());
  }
}
