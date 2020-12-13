package dev.mickael.adventofcode.day13;

import static java.util.Comparator.comparingInt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class ShuttleSearch {

  @Getter private final int earliestPossibleDeparture;

  @Getter private final List<Integer> busSchedules;

  @Getter(lazy = true)
  private final List<Integer> nextDepartures = computeNextDepartures();

  @Getter(lazy = true)
  private final int earliestBus = computeEarliestBus();

  public int waitTime() {
    return getNextDepartures().get(getEarliestBus()) - earliestPossibleDeparture;
  }

  public int earliestBusID() {
    return busSchedules.get(getEarliestBus());
  }

  public int computeEarliestBus() {
    return IntStream.range(0, getNextDepartures().size())
        .boxed()
        .min(comparingInt(getNextDepartures()::get))
        .get();
  }

  private List<Integer> computeNextDepartures() {
    assert busSchedules != null;
    return busSchedules.stream().map(this::nextDeparture).collect(Collectors.toList());
  }

  private int nextDeparture(int schedule) {
    // special case if there is a bus departing right at our earliest possible departure
    if (earliestPossibleDeparture % schedule == 0) {
      return earliestPossibleDeparture;
    }

    int tripNumber = earliestPossibleDeparture / schedule;
    return (tripNumber + 1) * schedule;
  }
}
