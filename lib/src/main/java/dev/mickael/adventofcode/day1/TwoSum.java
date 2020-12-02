package dev.mickael.adventofcode.day1;

import java.util.HashSet;
import java.util.Set;

public class TwoSum {
  public static int[] find(int[] input, int target) {
    int[] result = new int[2];
    Set<Integer> complements = new HashSet<>();

    for (int i = 0; i < input.length; i++) {
      int a = input[i];
      int compl = target - a;

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
