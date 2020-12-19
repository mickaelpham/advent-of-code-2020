package dev.mickael.adventofcode.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
public class ShuttleSearchTest {

  @Test
  void itIdentifiesTheEarliestNextBus() {
    List<Integer> schedules = List.of(7, 13, 59, 31, 19);
    var shuttleSearch =
        ShuttleSearch.builder().busSchedules(schedules).earliestPossibleDeparture(939).build();

    assertEquals(59, shuttleSearch.earliestBusID());
  }

  @Test
  void testExampleFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-13-example.txt");
    assertNotNull(inputPath);

    var lines = Files.readAllLines(Path.of(inputPath.toURI()));

    var earliestPossibleDeparture = Integer.parseInt(lines.get(0));
    var schedules =
        Arrays.stream(lines.get(1).split(","))
            .filter(s -> !s.equals("x"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

    var shuttleSearch =
        ShuttleSearch.builder()
            .busSchedules(schedules)
            .earliestPossibleDeparture(earliestPossibleDeparture)
            .build();

    assertEquals(59, shuttleSearch.earliestBusID());
  }

  @Test
  void testInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-13-input.txt");
    assertNotNull(inputPath);

    var lines = Files.readAllLines(Path.of(inputPath.toURI()));

    var earliestPossibleDeparture = Integer.parseInt(lines.get(0));
    var schedules =
        Arrays.stream(lines.get(1).split(","))
            .filter(s -> !s.equals("x"))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

    var shuttleSearch =
        ShuttleSearch.builder()
            .busSchedules(schedules)
            .earliestPossibleDeparture(earliestPossibleDeparture)
            .build();

    assertEquals(467, shuttleSearch.earliestBusID());
    long answer = (long) shuttleSearch.waitTime() * shuttleSearch.earliestBusID();

    log.info("ID of the earliest bus multiplied by wait time: " + answer);
    assertEquals(3269, answer);
  }

  @Test
  void findEarliestTimestampWithSynchronizedBus() {
    var schedules = new Integer[] {17, null, 13, 19};
    long timestamp = 0;
    boolean inSync = false;

    while (!inSync) {
      timestamp += schedules[0];
      inSync = true;

      for (int offset = 1; offset < schedules.length; offset++) {
        if (schedules[offset] == null) {
          continue;
        }

        long expectedDeparture = timestamp + offset;
        if (expectedDeparture % schedules[offset] != 0) {
          inSync = false;
          break;
        }
      }
    }

    assertEquals(3417, timestamp);
  }

  @Test
  void findEarliestTimestampWithSynchronizedBusExample4() {
    var schedules = new Integer[] {1789, 37, 47, 1889};
    long timestamp = 0;
    boolean inSync = false;

    while (!inSync) {
      timestamp += schedules[0];
      inSync = true;

      for (int offset = 1; offset < schedules.length; offset++) {
        long expectedDeparture = timestamp + offset;
        if (expectedDeparture % schedules[offset] != 0) {
          inSync = false;
          break;
        }
      }
    }

    assertEquals(1202161486, timestamp);
  }

  @Test
  @Disabled // because execution time is way too long here
  void findEarliestTimestampWithSynchronizedBusFromInputFile() {
    var schedules =
        new Integer[] {
          13, null, null, 41, null, null, null, null, null, null, null, null, null, 467, null, null,
          null, null, null, null, null, null, null, null, null, 19, null, null, null, null, 17,
          null, null, null, null, null, null, null, null, null, null, null, 29, null, 353, null,
          null, null, null, null, 37, null, null, null, null, null, null, null, null, null, null,
          null, null, null, null, null, null, 23
        };

    long timestamp = (100000000000000L / schedules[0]) * schedules[0];
    boolean inSync = false;

    while (!inSync) {
      timestamp += schedules[0];
      inSync = true;

      for (int offset = 1; offset < schedules.length; offset++) {
        if (schedules[offset] == null) {
          continue;
        }

        long expectedDeparture = timestamp + offset;
        if (expectedDeparture % schedules[offset] != 0) {
          inSync = false;
          break;
        }
      }
    }

    assertEquals(1202161486, timestamp);
  }
}
