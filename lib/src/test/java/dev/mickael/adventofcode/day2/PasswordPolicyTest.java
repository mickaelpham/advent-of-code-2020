package dev.mickael.adventofcode.day2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class PasswordPolicyTest {

  @Test
  void buildFromString() {
    var input = "2-9 x";
    var policy = PasswordPolicy.fromString(input);
    assertEquals("PasswordPolicy(minCount=2, maxCount=9, letter=x)", policy.toString());
  }

  @Test
  void testSampleValidPassword() {
    var policy = PasswordPolicy.fromString("1-5 c");
    var password = "acdc";

    assertTrue(policy.validate(password));
  }

  @Test
  void testSampleInvalidPassword() {
    var policy = PasswordPolicy.fromString("2-3 d");
    var password = "abcdefghijklmnopqrstuvwxyz";

    assertFalse(policy.validate(password));
  }

  @Test
  void testFromInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-2-input.txt");
    assertNotNull(inputPath);

    var validPasswords =
        Files.readAllLines(Path.of(inputPath.toURI())).stream()
            .map(line -> line.split(": "))
            .filter(
                input -> {
                  assert input.length == 2;
                  var policy = PasswordPolicy.fromString(input[0]);
                  return policy.validate(input[1]);
                })
            .collect(Collectors.toList());

    System.out.println("there are " + validPasswords.size() + " valid passwords");
  }
}
