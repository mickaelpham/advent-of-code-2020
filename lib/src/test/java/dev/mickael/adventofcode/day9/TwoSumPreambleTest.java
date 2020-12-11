package dev.mickael.adventofcode.day9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TwoSumPreambleTest {

  @Test
  void testTwoSumWithPreambleFromExampleFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-9-example.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToLong(Long::parseLong)
            .toArray();

    int preambleSize = 5;

    int left = 0;
    int right = preambleSize;
    long invalidNumber = -1;

    for (int i = right; i < nums.length; i++) {
      var input = Arrays.copyOfRange(nums, left, right);
      var result = TwoSum.find(input, nums[i]);

      if (Arrays.equals(result, new long[] {0, 0})) {
        invalidNumber = nums[i];
        log.info("first invalid number is {} at index {}", invalidNumber, i);
      }

      left++;
      right++;
    }

    assertEquals(127, invalidNumber);
  }

  @Test
  void testTwoSumWithPreambleFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-9-input.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToLong(Long::parseLong)
            .toArray();

    int preambleSize = 25;

    int left = 0;
    int right = preambleSize;
    long invalidNumber = -1;

    for (int i = right; i < nums.length; i++) {
      var input = Arrays.copyOfRange(nums, left, right);
      var result = TwoSum.find(input, nums[i]);

      if (Arrays.equals(result, new long[] {0, 0})) {
        invalidNumber = nums[i];
        log.info("first invalid number is {} at index {}", invalidNumber, i);
      }

      left++;
      right++;
    }

    assertEquals(1504371145, invalidNumber);
  }

  @Test
  void slidingWindowTest() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-9-input.txt");
    assertNotNull(inputPath);

    var nums =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .mapToLong(Long::parseLong)
            .toArray();

    long target = 1504371145;

    int left = 0;
    int right = 1;
    long runningSum = nums[0] + nums[1];

    while (right < nums.length && left <= right) {
      if (runningSum == target) {
        // found the continuous array
        break;
      } else if (runningSum < target) {
        // need to add to the window
        right++;
        runningSum += nums[right];
      } else {
        // need to decrease the window
        left++;
        runningSum -= nums[left - 1];
      }
    }

    System.out.println("found the array at " + left + ", " + right);
    var result = Arrays.copyOfRange(nums, left, right + 1);

    long min = Long.MAX_VALUE;
    long max = Long.MIN_VALUE;

    for (int i = 0; i < result.length; i++) {
      var n = result[i];

      if (min > n) {
        min = n;
      }

      if (max < n) {
        max = n;
      }
    }

    long sum = min + max;
    System.out.println("min = " + min + ", max = " + max + ", sum = " + sum);
    assertEquals(183278487, sum);
  }
}
