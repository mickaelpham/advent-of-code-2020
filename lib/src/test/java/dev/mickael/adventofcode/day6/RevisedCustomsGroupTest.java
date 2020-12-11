package dev.mickael.adventofcode.day6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RevisedCustomsGroupTest {

  @Test
  void testFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-6-input.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<RevisedCustomsGroup> customsGroups = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        customsGroups.add(RevisedCustomsGroup.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    var numYesQuestions =
        customsGroups.stream()
            .map(RevisedCustomsGroup::getYesAnswers)
            .map(Set::size)
            .reduce(0, Integer::sum);

    assertEquals(3398, numYesQuestions);
    log.info("found {} yes answers after revision", numYesQuestions);
  }
}
