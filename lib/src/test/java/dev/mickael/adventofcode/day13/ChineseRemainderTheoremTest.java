package dev.mickael.adventofcode.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.mickael.adventofcode.day13.ChineseRemainderTheorem.CongruenceConstraint;
import org.junit.jupiter.api.Test;

public class ChineseRemainderTheoremTest {

  @Test
  void testSearchBySieving() {
    // https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Search_by_sieving
    var crt =
        ChineseRemainderTheorem.builder()
            .constraint(CongruenceConstraint.builder().remainder(0).divisor(3).build())
            .constraint(CongruenceConstraint.builder().remainder(3).divisor(4).build())
            .constraint(CongruenceConstraint.builder().remainder(4).divisor(5).build())
            .build();

    assertEquals(39, crt.solve());
  }

  @Test
  void testSunTzu() {
    // https://en.wikipedia.org/wiki/Chinese_remainder_theorem#History
    var crt =
        ChineseRemainderTheorem.builder()
            .constraint(CongruenceConstraint.builder().remainder(2).divisor(3).build())
            .constraint(CongruenceConstraint.builder().remainder(3).divisor(5).build())
            .constraint(CongruenceConstraint.builder().remainder(2).divisor(7).build())
            .build();

    assertEquals(23, crt.solve());
  }

  @Test
  void testEarliestTimeStampWithSynchronizedBusExample1() {
    var schedules = new Integer[] {17, null, 13, 19};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(3417, crt.solve());
  }

  @Test
  void testEarliestTimeStampWithSynchronizedBusExample2() {
    var schedules = new Integer[] {67, 7, 59, 61};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(754018, crt.solve());
  }

  @Test
  void testEarliestTimeStampWithSynchronizedBusExample3() {
    var schedules = new Integer[] {67, null, 7, 59, 61};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(779210, crt.solve());
  }

  @Test
  void testEarliestTimeStampWithSynchronizedBusExample4() {
    var schedules = new Integer[] {67, 7, null, 59, 61};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(1261476, crt.solve());
  }

  @Test
  void testEarliestTimeStampWithSynchronizedBusExample5() {
    var schedules = new Integer[] {1789, 37, 47, 1889};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(1202161486, crt.solve());
  }

  @Test
  void findEarliestTimestampFromInputFile() {
    var schedules =
        new Integer[] {
          13, null, null, 41, null, null, null, null, null, null, null, null, null, 467, null, null,
          null, null, null, null, null, null, null, null, null, 19, null, null, null, null, 17,
          null, null, null, null, null, null, null, null, null, null, null, 29, null, 353, null,
          null, null, null, null, 37, null, null, null, null, null, null, null, null, null, null,
          null, null, null, null, null, null, 23
        };

    var crt = buildCRTfromSchedules(schedules);

    assertEquals(672754131923874L, crt.solve());
  }

  private ChineseRemainderTheorem buildCRTfromSchedules(Integer[] schedules) {
    var builder = ChineseRemainderTheorem.builder();

    for (int t = 0; t < schedules.length; t++) {
      if (schedules[t] == null) continue;

      // a + k ≡ b + k (mod n) for any integer k (compatibility with translation)
      builder.constraint(
          CongruenceConstraint.builder().remainder(-t).divisor(schedules[t]).build());
    }

    return builder.build();
  }
}
