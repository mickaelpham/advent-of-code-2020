package dev.mickael.adventofcode.day13;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Builder
@Data
public class ChineseRemainderTheorem {

  @Singular private final List<CongruenceConstraint> constraints;

  public long solve() {
    if (constraints.size() < 2) {
      throw new IllegalStateException("there are less then 2 constraints");
    }

    // sort constraints by their divisors in descending order
    var constraints =
        getConstraints().stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toCollection(ArrayDeque::new));

    var constraint = constraints.removeFirst();

    while (!constraints.isEmpty()) {
      var nextConstraint = constraints.removeFirst();

      // Find a congruent for the next constraint using x = k * divisor + remainder
      // (from the current constraint)
      int k = 0;
      while (!nextConstraint.isCongruent(constraint.x(k))) k++;

      // "Merge" both constraints
      constraint =
          CongruenceConstraint.builder()
              .remainder(constraint.x(k))
              .divisor(constraint.divisor * nextConstraint.divisor)
              .build();
    }

    return constraint.remainder;
  }

  @Builder
  public static class CongruenceConstraint implements Comparable<CongruenceConstraint> {
    public long remainder;
    public long divisor;

    /**
     * Return a value respecting this specific constraint
     *
     * @param k factor
     * @return value of k * module + remainder
     */
    public long x(int k) {
      return k * divisor + remainder;
    }

    /**
     * Verifies that a given integer is congruent with our modulo
     *
     * @param x integer to check
     * @return true if x and our remainder are congruent
     */
    public boolean isCongruent(long x) {
      return (x - remainder) % divisor == 0;
    }

    @Override
    public int compareTo(CongruenceConstraint o) {
      return Long.compare(divisor, o.divisor);
    }
  }
}
