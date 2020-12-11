package dev.mickael.adventofcode.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/** It's a game of life variant */
@Builder
public class SeatingSystem {

  private static final Map<Character, Seat> LEGEND =
      Map.of(
          'L', Seat.EMPTY,
          '.', Seat.GROUND,
          '#', Seat.OCCUPIED);

  public static SeatingSystem fromInput(char[][] input) {
    Seat[][] grid = new Seat[input.length][input[0].length];

    for (int row = 0; row < input.length; row++) {
      for (int col = 0; col < input[0].length; col++) {
        grid[row][col] = LEGEND.get(input[row][col]);
      }
    }

    return builder().grid(grid).build();
  }

  private static final long MAX_OCCUPIED_SEATS = 4;

  @Getter private final Seat[][] grid;

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
    var it = new ArrayList<Coordinate>();

    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        var seat = Coordinate.builder().row(row).col(col).build();

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
  private void flip(List<Coordinate> seats) {
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
  private boolean becomesOccupied(Coordinate seat) {
    return adjacent(seat).stream().noneMatch(s -> s == Seat.OCCUPIED);
  }

  /**
   * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat
   * becomes empty.
   *
   * @param seat Coordinates of the seat
   * @return true if the seat becomes empty
   */
  private boolean becomesEmpty(Coordinate seat) {
    return adjacent(seat).stream().filter(s -> s == Seat.OCCUPIED).count() >= MAX_OCCUPIED_SEATS;
  }

  /**
   * Return the seat-uation (ah ah) at the given coordinate. Returns the ground if the seat
   * coordinate are out of bound.
   *
   * @param s Coordinate of the seat to check
   * @return the seat at the given coordinate
   */
  private Seat get(Coordinate s) {
    if (s.row < 0 || s.row >= grid.length) {
      return Seat.GROUND;
    } else if (s.col < 0 || s.col >= grid[0].length) {
      return Seat.GROUND;
    }

    return grid[s.row][s.col];
  }

  /**
   * Return the adjacent seats for the given coordinate
   *
   * @param s Seat coordinates to check from
   * @return a List of seats
   */
  private List<Seat> adjacent(Coordinate s) {
    return List.of(
        get(s.topLeft()),
        get(s.topMid()),
        get(s.topRight()),
        get(s.left()),
        get(s.right()),
        get(s.bottomLeft()),
        get(s.bottomMid()),
        get(s.bottomRight()));
  }

  public enum Seat {
    GROUND,
    OCCUPIED,
    EMPTY
  }

  @Builder
  private static class Coordinate {
    int row;
    int col;

    public Coordinate topLeft() {
      return Coordinate.builder().row(row - 1).col(col - 1).build();
    }

    public Coordinate topMid() {
      return Coordinate.builder().row(row - 1).col(col).build();
    }

    public Coordinate topRight() {
      return Coordinate.builder().row(row - 1).col(col + 1).build();
    }

    public Coordinate left() {
      return Coordinate.builder().row(row).col(col - 1).build();
    }

    public Coordinate right() {
      return Coordinate.builder().row(row).col(col + 1).build();
    }

    public Coordinate bottomLeft() {
      return Coordinate.builder().row(row + 1).col(col - 1).build();
    }

    public Coordinate bottomMid() {
      return Coordinate.builder().row(row + 1).col(col).build();
    }

    public Coordinate bottomRight() {
      return Coordinate.builder().row(row + 1).col(col + 1).build();
    }
  }
}
