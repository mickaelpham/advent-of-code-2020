package dev.mickael.adventofcode.day10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public class Joltage {

  private static final int MAX_DIFFERENCE = 3;

  private final int[] adapters;

  private final Map<Integer, Long> memoizedResults = new HashMap<>();

  private Set<Integer> adapterSet;

  /**
   * Chain that uses all of the adapters to connect the charging outlet to your device's built-in
   * adapter and count the joltage differences between the charging outlet, the adapters, and your
   * device.
   *
   * @return number of 1-jolt differences multiplied by the number of 3-jolt differences
   */
  public int joltDifference() {
    int[] differences = new int[MAX_DIFFERENCE + 1];

    // first element (from outlet to first adapter
    differences[adapters[0]] += 1;

    for (int i = 0; i < adapters.length; i++) {
      int curr = adapters[i];

      // next element is either the next adapter of the built-in device adapter (always +3)
      int next = (i == adapters.length - 1) ? curr + 3 : adapters[i + 1];

      differences[next - curr] += 1;
    }

    return differences[1] * differences[3];
  }

  /**
   * To completely determine whether you have enough adapters, you'll need to figure out how many
   * different ways they can be arranged. Every arrangement needs to connect the charging outlet to
   * your device. The previous rules about when adapters can successfully connect still apply.
   *
   * @return total number of arrangements
   */
  public long arrangements() {
    adapterSet = Arrays.stream(adapters).boxed().collect(Collectors.toSet());

    // add 0 to the num set for handling starting position
    adapterSet.add(0);

    return startingFrom(0);
  }

  private long startingFrom(int position) {
    int target = adapters[adapters.length - 1];

    if (memoizedResults.containsKey(position)) {
      return memoizedResults.get(position);
    } else if (!adapterSet.contains(position) || position > target) {
      // invalid position
      return 0;
    } else if (position == target) {
      return 1;
    }

    long result =
        startingFrom(position + 1) + startingFrom(position + 2) + startingFrom(position + 3);

    memoizedResults.put(position, result);
    return result;
  }
}
