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
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class PassportTest {

  @Test
  void testParseSimpleInput() {
    var input = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd byr:1937 iyr:2017 cid:147 hgt:183cm";
    var expected = Set.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "cid", "hgt");
    var passport = Passport.fromString(input);

    assertEquals(expected, passport.getFields().keySet());
  }

  @Test
  void testParseInputWithNewLineCharacter() {
    var input = "ecl:gry\npid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm";
    var expected = Set.of("ecl", "pid", "eyr", "hcl", "byr", "iyr", "cid", "hgt");
    var passport = Passport.fromString(input);

    assertEquals(expected, passport.getFields().keySet());
  }

  @Test
  void testExampleInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-4-example.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<Passport> passports = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        passports.add(Passport.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    assertEquals(4, passports.size());
    System.out.println(passports);

    List<Passport> validPassports =
        passports.stream().filter(Passport::isValid).collect(Collectors.toList());
    assertEquals(2, validPassports.size());
  }

  @Test
  void testInputFileAndCountValidPassport() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-4-input.txt");
    assertNotNull(inputPath);

    var input = Files.readAllLines(Path.of(inputPath.toURI()));

    List<Passport> passports = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    Iterator<String> iterator = input.iterator();

    while (iterator.hasNext()) {
      var line = iterator.next();

      if (!line.isBlank()) {
        sb.append(line).append("\n");
      }

      if (line.isBlank() || !iterator.hasNext()) {
        passports.add(Passport.fromString(sb.toString()));
        sb = new StringBuilder();
      }
    }

    List<Passport> validPassports =
        passports.stream().filter(Passport::isValid).collect(Collectors.toList());
    assertEquals(230, validPassports.size());
    System.out.println("there are " + validPassports.size() + " valid passports");
  }
}
