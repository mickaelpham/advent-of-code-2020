package dev.mickael.adventofcode.day7;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bag {

  private final String color;

  @Builder.Default private final Set<String> containedBy = new HashSet<>();
}
