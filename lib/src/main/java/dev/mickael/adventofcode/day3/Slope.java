package dev.mickael.adventofcode.day3;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Slope {

  private final int row;
  private final int col;
}
