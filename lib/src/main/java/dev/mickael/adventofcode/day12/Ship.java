package dev.mickael.adventofcode.day12;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Ship {

  private static final Pattern MOVEMENT =
      Pattern.compile("\\A(?<direction>[NSEWF])(?<value>\\d+)\\z");

  private static final Pattern ROTATION = Pattern.compile("\\A(?<rotation>[LR])(?<degree>\\d+)\\z");

  private int x;

  private int y;

  @Builder.Default private Direction facing = Direction.EAST;

  public int manhattanDistance() {
    return Math.abs(x) + Math.abs(y);
  }

  public void exec(String instruction) {
    var movementMatcher = MOVEMENT.matcher(instruction);
    var rotationMatcher = ROTATION.matcher(instruction);

    var movementMatch = movementMatcher.matches();
    var rotationMatch = rotationMatcher.matches();

    if (movementMatch) {
      applyMovement(
          Direction.valueOfLabel(movementMatcher.group("direction")),
          Integer.parseInt(movementMatcher.group("value")));
    } else if (rotationMatch) {
      applyRotation(
          Rotation.valueOfLabel(rotationMatcher.group("rotation")),
          Integer.parseInt(rotationMatcher.group("degree")));
    }
  }

  private void applyMovement(Direction direction, int value) {
    if (direction == Direction.FORWARD) {
      direction = facing;
    }

    switch (direction) {
      case NORTH:
        y -= value;
        break;
      case EAST:
        x += value;
        break;
      case SOUTH:
        y += value;
        break;
      case WEST:
        x -= value;
        break;
    }
  }

  private void applyRotation(Rotation rotation, int degree) {
    if (rotation == Rotation.LEFT) {
      degree = -degree;
    }

    var nowFacing = facing.label;
    nowFacing += degree;
    nowFacing %= 360;

    if (nowFacing < 0) {
      nowFacing += 360;
    }

    facing = Direction.valueOf(nowFacing);
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
}
