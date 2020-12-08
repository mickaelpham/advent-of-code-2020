package dev.mickael.adventofcode.day8;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@EqualsAndHashCode
public class BootProgram {

  private static final Pattern INSTRUCTION =
      Pattern.compile("\\A(?<op>nop|acc|jmp)\\s(?<arg>[+-]\\d+)\\z");

  private final List<String> lines;

  @Builder.Default private final Set<Integer> previouslyExecuted = new HashSet<>();

  @Getter private int accumulator;

  @Builder.Default private int debugLine = -1;

  /**
   * Start a debugging session from the current cursor. Preserves the internal state (accumulator
   * and previously executed lines) before executing the program until we either:
   *
   * <ol>
   *   <li>enter an infinite loop
   *   <li>reach the end of the program
   * </ol>
   *
   * <p>If we enter an infinite loop, restore the internal state before exiting, otherwise preserve
   * the current state value.
   *
   * @param cursor Line to debug from (= swap the instruction at this line)
   * @return -1 if we entered an infinite loop in this debug session
   */
  public int debugFrom(int cursor) {
    debugLine = cursor;
    int accumulatorCopy = accumulator;
    var previouslyExecutedCopy = Set.copyOf(previouslyExecuted);

    while (cursor != -1 && cursor < lines.size()) {
      cursor = exec(cursor);
    }

    // Don't forget to restore the state of the program before exiting debug mode
    // if we ended up in an infinite loop
    if (cursor == -1) {
      accumulator = accumulatorCopy;

      previouslyExecuted.clear();
      previouslyExecuted.addAll(previouslyExecutedCopy);

      debugLine = -1;
    }

    return cursor;
  }

  /**
   * Execute one line of the program at the given cursor. Modifies the accumulator state if the
   * instruction tells us so.
   *
   * @param lineAt Cursor position to execute
   * @return next line to execute, or -1 if we detect an infinite loops
   */
  public int exec(int lineAt) {
    if (previouslyExecuted.contains(lineAt)) return -1;
    previouslyExecuted.add(lineAt);

    var m = INSTRUCTION.matcher(lines.get(lineAt));
    var isValidLine = m.matches();
    assert isValidLine : "invalid instruction at line " + lineAt;

    var op = m.group("op");

    // DEBUG MODE: swap the operation if cursor is at debug line
    if (debugLine == lineAt) {
      if (op.equals("nop")) {
        op = "jmp";
      } else if (op.equals("jmp")) {
        op = "nop";
      }
    }

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
