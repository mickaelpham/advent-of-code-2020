package dev.mickael.adventofcode.day8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class BootProgramTest {

  @Test
  void testInputFile() throws URISyntaxException, IOException {
    var inputPath = getClass().getClassLoader().getResource("day-8-input.txt");
    assertNotNull(inputPath);

    var lines = Files.readAllLines(Path.of(inputPath.toURI()));
    var program = BootProgram.builder().lines(lines).build();

    int cursor = 0;
    while (cursor != -1) {
      cursor = program.exec(cursor);
    }

    assertEquals(1814, program.getAccumulator());
  }
}
