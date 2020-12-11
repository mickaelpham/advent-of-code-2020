package dev.mickael.adventofcode.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StrictPassportTest {
  @Test
  void testExampleInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-4-example-2.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<StrictPassport> passports = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        passports.add(StrictPassport.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    assertEquals(8, passports.size());

    List<StrictPassport> validPassports =
        passports.stream().filter(StrictPassport::isValid).collect(Collectors.toList());
    assertEquals(4, validPassports.size());
    log.info("there are {} valid passports (strict validation)", validPassports.size());
  }

  @Test
  void testInputFileAndCountValidPassport() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-4-input.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<StrictPassport> passports = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        passports.add(StrictPassport.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    List<StrictPassport> validPassports =
        passports.stream().filter(StrictPassport::isValid).collect(Collectors.toList());
    assertEquals(156, validPassports.size());
    log.info("there are {} valid passports (strict validation)", validPassports.size());
  }
}
