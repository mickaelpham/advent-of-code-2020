package dev.mickael.adventofcode.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BootProgramTest {

  private static final String EXAMPLE_FILENAME = "day-8-example.txt";
  private static final String INPUT_FILENAME = "day-8-input.txt";

  private static BootProgram loadProgram(String filename) {
    var inputPath = BootProgramTest.class.getClassLoader().getResource(filename);
    assertNotNull(inputPath, "could not load resource: " + filename);

    List<String> lines = null;
    try {
      lines = Files.readAllLines(Path.of(inputPath.toURI()));
    } catch (IOException | URISyntaxException ignored) {
    }
    assertNotNull(lines);

    return BootProgram.builder().lines(lines).build();
  }

  @Test
  void testExample() {
    var program = loadProgram(EXAMPLE_FILENAME);

    int cursor = 0;
    while (cursor != -1) {
      cursor = program.exec(cursor);
    }

    assertEquals(5, program.getAccumulator());
  }

  @Test
  void testInputFile() {
    var program = loadProgram(INPUT_FILENAME);

    int cursor = 0;
    while (cursor != -1) {
      cursor = program.exec(cursor);
    }

    assertEquals(1814, program.getAccumulator());
  }

  @Test
  void testDebugExampleFile() {
    var program = loadProgram(EXAMPLE_FILENAME);

    int cursor = 0;
    while (program.debugFrom(cursor) == -1) {
      cursor = program.exec(cursor);
    }

    assertEquals(8, program.getAccumulator());
  }

  @Test
  void testDebugInputFile() {
    var program = loadProgram(INPUT_FILENAME);

    int cursor = 0;
    while (program.debugFrom(cursor) == -1) {
      cursor = program.exec(cursor);
    }

    assertEquals(1056, program.getAccumulator());
  }
}
