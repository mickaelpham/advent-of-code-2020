package dev.mickael.adventofcode.day5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SeatTest {

  @Test
  void decodeRowUsingStringInput() {
    assertEquals(44, Seat.decode("FBFBBFF"));
  }

  @Test
  void decodeColUsingStringInput() {
    assertEquals(5, Seat.decode("RLR"));
  }

  @Test
  void throwExceptionWithUnrecognizedCharacter() {
    var invalidInput = "FBABF";

    var ex = assertThrows(IllegalArgumentException.class, () -> Seat.decode(invalidInput));

    assertEquals("could not decode character A with input " + invalidInput, ex.getMessage());
  }

  @Test
  void decodeSampleSeatIDs() {
    Map<String, Integer> testCases =
        Map.of(
            "FBFBBFFRLR", 357,
            "BFFFBBFRRR", 567,
            "FFFBBBFRRR", 119,
            "BBFFBBFRLL", 820);
    Seat seat;

    for (var entry : testCases.entrySet()) {
      seat = Seat.fromString(entry.getKey());
      assertEquals(entry.getValue(), seat.getID());
    }
  }

  @Test
  void findHighestSeatIDInFileInput() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-5-input.txt");
    assertNotNull(inputPath);

    var result =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Seat::fromString)
            .map(Seat::getID)
            .max(Integer::compare)
            .orElse(0);

    assertEquals(989, result);
    System.out.println("the highest seat ID is " + result);
  }

  @Test
  void findMySeatAsMissingFromInput() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-5-input.txt");
    assertNotNull(inputPath);

    var sortedSeatIDs =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(Seat::fromString)
            .mapToInt(Seat::getID)
            .sorted()
            .toArray();

    // find the missing seat in the array
    // TODO improve runtime to O(log n) instead of O(n)
    int expected = sortedSeatIDs[0];
    int mySeat = -1;
    for (int val : sortedSeatIDs) {
      if (expected != val) {
        mySeat = val - 1;
        break;
      }
      expected++;
    }

    assertEquals(548, mySeat);
    System.out.println("my seat ID is " + mySeat);
  }
}
