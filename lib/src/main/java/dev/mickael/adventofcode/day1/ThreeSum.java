package dev.mickael.adventofcode.day1;

import java.util.HashSet;
import java.util.Set;

public class ThreeSum {

  public static int[] exec(int[] input, int target) {
    for (int i = 0; i < input.length; i++) {
      var a = input[i];
      Set<Integer> complements = new HashSet<>();

      for (int j = i + 1; j < input.length; j++) {
        var b = input[j];
        var c = target - b - a;

        if (complements.contains(c)) {
          return new int[] {a, b, c};
        }

        complements.add(b);
      }
    }

    return new int[0];
  }
}
