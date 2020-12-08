package dev.mickael.adventofcode.day8;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BootProgram {

  private static final Pattern INSTRUCTION =
      Pattern.compile("\\A(?<op>nop|acc|jmp)\\s(?<arg>[+-]\\d+)\\z");

  private final List<String> lines;

  @Builder.Default private final Set<Integer> previouslyExecuted = new HashSet<>();

  private int accumulator;

  public int exec(int lineAt) {
    if (previouslyExecuted.contains(lineAt)) return -1;
    previouslyExecuted.add(lineAt);

    var m = INSTRUCTION.matcher(lines.get(lineAt));
    assert m.matches();

    var op = m.group("op");
    int arg = Integer.parseInt(m.group("arg"));

    int nextLine = -1;

    switch (op) {
      case "nop":
        nextLine = lineAt + 1;
        break;
      case "acc":
        accumulator += arg;
        nextLine = lineAt + 1;
        break;
      case "jmp":
        nextLine = lineAt + arg;
        break;
    }

    return nextLine;
  }
}
