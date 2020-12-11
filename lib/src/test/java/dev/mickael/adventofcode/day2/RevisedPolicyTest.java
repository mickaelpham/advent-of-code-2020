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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RevisedPolicyTest {

  @Test
  void buildFromString() {
    var input = "2-9 x";
    var policy = RevisedPolicy.fromString(input);
    assertEquals("RevisedPolicy(positions=[2, 9], letter=x)", policy.toString());
  }

  @Test
  void testSampleValidPassword() {
    var policy = RevisedPolicy.fromString("1-5 c");
    var password = "clolbp";

    assertTrue(policy.validate(password));
  }

  @Test
  void testInvalidBecauseLetterAtBothPosition() {
    var policy = RevisedPolicy.fromString("1-2 b");
    var password = "bbc";

    assertFalse(policy.validate(password));
  }

  @Test
  void testSampleInvalidPassword() {
    var policy = RevisedPolicy.fromString("2-3 d");
    var password = "abcdefghijklmnopqrstuvwxyz";

    assertFalse(policy.validate(password));
  }

  @Test
  void testSampleInvalidBecauseTooShort() {
    var policy = RevisedPolicy.fromString("9-19 a");
    var password = "aaa";

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
                  var policy = RevisedPolicy.fromString(input[0]);
                  return policy.validate(input[1]);
                })
            .collect(Collectors.toList());

    log.info("there are {} valid password", validPasswords.size());
  }
}
