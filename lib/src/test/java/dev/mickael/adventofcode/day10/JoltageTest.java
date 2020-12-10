package dev.mickael.adventofcode.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class JoltageTest {

  @Test
  void testJoltageDifferenceWithExample1File() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-10-example-1.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();

    int diff1 = 0;
    int diff3 = 0;

    if (nums[0] == 1) {
      diff1++;
    } else if (nums[0] == 3) {
      diff3++;
    }

    for (int i = 0; i < nums.length; i++) {
      int n = nums[i];

      // last element
      if (i == nums.length - 1) {
        diff3++;
        continue;
      }

      int next = nums[i + 1];
      int diff = next - n;

      if (diff == 1) {
        diff1++;
      } else if (diff == 3) {
        diff3++;
      }
    }

    System.out.println("diff1 = " + diff1 + "; diff3 = " + diff3);
    assertEquals(7, diff1);
    assertEquals(5, diff3);
  }

  @Test
  void testJoltageDifference() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-10-input.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();

    int diff1 = 0;
    int diff3 = 0;

    if (nums[0] == 1) {
      diff1++;
    } else if (nums[0] == 3) {
      diff3++;
    }

    for (int i = 0; i < nums.length; i++) {
      int n = nums[i];

      // last element
      if (i == nums.length - 1) {
        diff3++;
        continue;
      }

      int next = nums[i + 1];
      int diff = next - n;

      if (diff == 1) {
        diff1++;
      } else if (diff == 3) {
        diff3++;
      }
    }

    System.out.println("diff1 = " + diff1 + "; diff3 = " + diff3);
    assertEquals(65, diff1);
    assertEquals(29, diff3);
  }

  @Test
  void countArrangementsExample1File() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-10-example-1.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();
    int target = nums[nums.length - 1];

    var numSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
    // add 0 to the num set for handling starting position
    numSet.add(0);

    long numArrangements = startingFrom(0, target, numSet);
    ;

    assertEquals(8, numArrangements);
  }

  @Test
  void countArrangementsExample2File() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-10-example-2.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();
    int target = nums[nums.length - 1];

    var numSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
    // add 0 to the num set for handling starting position
    numSet.add(0);

    long numArrangements = startingFrom(0, target, numSet);
    ;

    assertEquals(19208, numArrangements);
  }

  @Test
  void countArrangementsInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-10-input.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToInt(Integer::parseInt)
            .sorted()
            .toArray();
    int target = nums[nums.length - 1];

    var numSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
    // add 0 to the num set for handling starting position
    numSet.add(0);

    long numArrangements = startingFrom(0, target, numSet);
    ;

    assertEquals(2024782584832L, numArrangements);
  }

  private final Map<Integer, Long> memoizedResults = new HashMap<>();

  private long startingFrom(int position, int target, Set<Integer> numSet) {
    if (!numSet.contains(position) || position > target) {
      // invalid position
      return 0;
    } else if (position == target) {
      return 1;
    } else if (memoizedResults.containsKey(position)) {
      return memoizedResults.get(position);
    }

    long result =
        startingFrom(position + 1, target, numSet)
            + startingFrom(position + 2, target, numSet)
            + startingFrom(position + 3, target, numSet);

    memoizedResults.put(position, result);
    return result;
  }
}
