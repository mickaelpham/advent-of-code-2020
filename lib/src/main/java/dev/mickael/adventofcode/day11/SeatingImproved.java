package dev.mickael.adventofcode.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** It's a game of life variant */
@Builder
public class SeatingImproved {

  private static final Map<Character, Seat> LEGEND =
      Map.of(
          'L', Seat.EMPTY,
          '.', Seat.GROUND,
          '#', Seat.OCCUPIED);

  private static final long MAX_OCCUPIED_SEATS = 5;

  @Getter private final Seat[][] grid;

  public static SeatingImproved fromInput(char[][] input) {
    Seat[][] grid = new Seat[input.length][input[0].length];

    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[0].length; col++) {
        grid[row][col] = LEGEND.get(input[row][col]);
      }
    }

    return builder().grid(grid).build();
  }

  public int numEmpty() {
    int result = 0;

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] == Seat.OCCUPIED) result++;
      }
    }

    return result;
  }

  public boolean next() {
    var it = new ArrayList<Coordinates>();

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        var seat = Coordinates.builder().row(row).col(col).build();

        switch (get(seat)) {
          case OCCUPIED:
            if (becomesEmpty(seat)) it.add(seat);
            break;
          case EMPTY:
            if (becomesOccupied(seat)) it.add(seat);
            break;
        }
      }
    }

    flip(it);
    return it.size() > 0;
  }

  /**
   * Flip (occupied <=> empty) a list of seats on the grid
   *
   * @param seats to flip
   */
  private void flip(List<Coordinates> seats) {
    for (var s : seats) {
      if (grid[s.row][s.col] == Seat.OCCUPIED) {
        grid[s.row][s.col] = Seat.EMPTY;
      } else {
        grid[s.row][s.col] = Seat.OCCUPIED;
      }
    }
  }

  /**
   * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes
   * occupied
   *
   * @param seat Coordinates of the seat
   * @return true if the seat becomes occupied
   */
  private boolean becomesOccupied(Coordinates seat) {
    return firstSeenSeats(seat).stream().noneMatch(s -> s == Seat.OCCUPIED);
  }

  /**
   * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat
   * becomes empty.
   *
   * @param seat Coordinates of the seat
   * @return true if the seat becomes empty
   */
  private boolean becomesEmpty(Coordinates seat) {
    return firstSeenSeats(seat).stream().filter(s -> s == Seat.OCCUPIED).count()
        >= MAX_OCCUPIED_SEATS;
  }

  /**
   * Return the seat-uation (ah ah) at the given coordinate. Returns the ground if the seat
   * coordinate are out of bound.
   *
   * @param s Coordinate of the seat to check
   * @return the seat at the given coordinate
   */
  private Seat get(Coordinates s) {
    if (s.row < 0 || s.row >= grid.length) {
      return Seat.OUT_OF_BOUND;
    } else if (s.col < 0 || s.col >= grid[0].length) {
      return Seat.OUT_OF_BOUND;
    }

    return grid[s.row][s.col];
  }

  private Seat nextSeat(Coordinates fromCoordinates, Direction direction) {
    var seat = fromCoordinates.in(direction);

    while (get(seat) == Seat.GROUND) {
      seat = seat.in(direction);
    }

    return get(seat);
  }

  /**
   * Return the adjacent seats for the given coordinate
   *
   * @param seat Seat coordinates to check from
   * @return a List of seats
   */
  private List<Seat> firstSeenSeats(Coordinates seat) {
    return List.of(
        nextSeat(seat, Direction.TOP_LEFT),
        nextSeat(seat, Direction.TOP),
        nextSeat(seat, Direction.TOP_RIGHT),
        nextSeat(seat, Direction.LEFT),
        nextSeat(seat, Direction.RIGHT),
        nextSeat(seat, Direction.BOTTOM_LEFT),
        nextSeat(seat, Direction.BOTTOM),
        nextSeat(seat, Direction.BOTTOM_RIGHT));
  }

  public enum Direction {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    LEFT,
    RIGHT,
    BOTTOM_LEFT,
    BOTTOM,
    BOTTOM_RIGHT
  }

  public enum Seat {
    GROUND,
    OCCUPIED,
    EMPTY,
    OUT_OF_BOUND
  }

  @Builder
  private static class Coordinates {
    int row;
    int col;

    public Coordinates in(Direction dir) {
      switch (dir) {
        case TOP_LEFT:
          return Coordinates.builder().row(row - 1).col(col - 1).build();
        case TOP:
          return Coordinates.builder().row(row - 1).col(col).build();
        case TOP_RIGHT:
          return Coordinates.builder().row(row - 1).col(col + 1).build();
        case LEFT:
          return Coordinates.builder().row(row).col(col - 1).build();
        case RIGHT:
          return Coordinates.builder().row(row).col(col + 1).build();
        case BOTTOM_LEFT:
          return Coordinates.builder().row(row + 1).col(col - 1).build();
        case BOTTOM:
          return Coordinates.builder().row(row + 1).col(col).build();
        case BOTTOM_RIGHT:
          return Coordinates.builder().row(row + 1).col(col + 1).build();
        default:
          return this;
      }
    }
  }
}
