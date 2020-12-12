package dev.mickael.adventofcode.day12;

import dev.mickael.adventofcode.day12.Ship.Direction;
import dev.mickael.adventofcode.day12.Ship.Rotation;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShipWaypoint {

  private static final Pattern MOVEMENT =
      Pattern.compile("\\A(?<direction>[NSEW])(?<value>\\d+)\\z");

  private static final Pattern ROTATION = Pattern.compile("\\A(?<rotation>[LR])(?<degree>\\d+)\\z");

  private static final Pattern FORWARD = Pattern.compile("\\AF(?<value>\\d+)\\z");

  @Builder.Default private final Waypoint waypoint = new Waypoint();

  private int x;

  private int y;

  public int manhattanDistance() {
    return Math.abs(x) + Math.abs(y);
  }

  public void exec(String instruction) {
    var movementMatcher = MOVEMENT.matcher(instruction);
    var rotationMatcher = ROTATION.matcher(instruction);
    var forwardMatcher = FORWARD.matcher(instruction);

    var movementMatch = movementMatcher.matches();
    var rotationMatch = rotationMatcher.matches();
    var forwardMatch = forwardMatcher.matches();

    if (movementMatch) {
      applyWaypointMovement(
          Direction.valueOfLabel(movementMatcher.group("direction")),
          Integer.parseInt(movementMatcher.group("value")));
    } else if (rotationMatch) {
      applyWaypointRotation(
          Rotation.valueOfLabel(rotationMatcher.group("rotation")),
          Integer.parseInt(rotationMatcher.group("degree")));
    } else if (forwardMatch) {
      forward(Integer.parseInt(forwardMatcher.group("value")));
    }
  }

  private void forward(int value) {
    x += value * waypoint.x;
    y += value * waypoint.y;
  }

  private void applyWaypointMovement(Direction direction, int value) {
    switch (direction) {
      case NORTH:
        waypoint.y += value;
        break;
      case EAST:
        waypoint.x += value;
        break;
      case SOUTH:
        waypoint.y -= value;
        break;
      case WEST:
        waypoint.x -= value;
        break;
    }
  }

  private void applyWaypointRotation(Rotation rotation, int degree) {
    int tmp;

    if (rotation == Rotation.LEFT) {
      // counter-clockwise
      switch (degree) {
        case 90:
          tmp = waypoint.x;
          waypoint.x = -waypoint.y;
          waypoint.y = tmp;
          break;
        case 180:
          waypoint.x = -waypoint.x;
          waypoint.y = -waypoint.y;
          break;
        case 270:
          tmp = waypoint.y;
          waypoint.y = -waypoint.x;
          waypoint.x = tmp;
          break;
      }

    } else if (rotation == Rotation.RIGHT) {
      // clockwise
      switch (degree) {
        case 90:
          tmp = waypoint.y;
          waypoint.y = -waypoint.x;
          waypoint.x = tmp;
          break;
        case 180:
          waypoint.x = -waypoint.x;
          waypoint.y = -waypoint.y;
          break;
        case 270:
          tmp = waypoint.x;
          waypoint.x = -waypoint.y;
          waypoint.y = tmp;
          break;
      }
    }
  }

  @AllArgsConstructor
  public enum Direction {
    NORTH(0),
    SOUTH(180),
    EAST(90),
    WEST(270),
    FORWARD(-1);

    private final int label;

    public static Direction valueOf(int label) {
      for (Direction d : values()) {
        if (d.label == label) {
          return d;
        }
      }

      return null;
    }

    public static Direction valueOfLabel(String label) {
      switch (label) {
        case "N":
          return NORTH;
        case "E":
          return EAST;
        case "S":
          return SOUTH;
        case "W":
          return WEST;
        case "F":
          return FORWARD;
        default:
          return null;
      }
    }
  }

  @AllArgsConstructor
  public enum Rotation {
    LEFT("L"),
    RIGHT("R");

    private final String label;

    public static Rotation valueOfLabel(String label) {
      switch (label) {
        case "L":
          return LEFT;
        case "R":
          return RIGHT;
        default:
          return null;
      }
    }
  }

  private static class Waypoint {
    private int x = 10;
    private int y = 1;
  }
}
