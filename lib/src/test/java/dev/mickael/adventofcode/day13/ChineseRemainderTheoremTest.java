package dev.mickael.adventofcode.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.mickael.adventofcode.day13.ChineseRemainderTheorem.CongruenceConstraint;
import org.junit.jupiter.api.Disabled;
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

  @Disabled
  @Test
  void testEarliestTimeStampWithSynchronizedBus() {
    var schedules = new Integer[] {17, null, 13, 19};
    var crt = buildCRTfromSchedules(schedules);

    assertEquals(3417, crt.solve());
  }

  private ChineseRemainderTheorem buildCRTfromSchedules(Integer[] schedules) {
    var builder = ChineseRemainderTheorem.builder();

    for (int t = 0; t < schedules.length; t++) {
      if (schedules[t] == null) continue;

      builder.constraint(
          CongruenceConstraint.builder().remainder(-t).divisor(schedules[t]).build());
    }

    return builder.build();
  }
}
