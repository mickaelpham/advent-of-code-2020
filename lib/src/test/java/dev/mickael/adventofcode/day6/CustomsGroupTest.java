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
import org.junit.jupiter.api.Test;

public class CustomsGroupTest {

  @Test
  void testFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-6-input.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<CustomsGroup> customsGroups = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        customsGroups.add(CustomsGroup.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    var numYesQuestions =
        customsGroups.stream()
            .map(CustomsGroup::getYesAnswers)
            .map(Set::size)
            .reduce(0, Integer::sum);

    assertEquals(6947, numYesQuestions);
    System.out.println("initially found " + numYesQuestions + " yes answers");
  }
}
