package dev.mickael.adventofcode.day9;

import java.util.HashSet;
import java.util.Set;

public class TwoSum {
  public static long[] find(long[] input, long target) {
    var result = new long[2];
    Set<Long> complements = new HashSet<>();

    for (int i = 0; i < input.length; i++) {
      var a = input[i];
      var compl = target - a;

      if (complements.contains(compl)) {
        result[0] = a;
        result[1] = compl;

        return result;
      }

      complements.add(a);
    }

    return result;
  }
}
