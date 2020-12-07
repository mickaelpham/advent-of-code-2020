package dev.mickael.adventofcode.day7;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Rule {
  private static final Pattern OUTER_COLOR =
      Pattern.compile("\\A(?<color>[a-z]+\\s[a-z]+) bags contain");

  private static final Pattern INNER_BAGS_QTY_AND_COLOR =
      Pattern.compile("(?<qty>[0-9]+)\\s(?<color>[a-z]+\\s[a-z]+)");

  private final String outerBagColor;
  private final Map<String, Integer> innerBags;

  public static Rule fromString(String input) {
    var outerMatcher = OUTER_COLOR.matcher(input);
    var foundOuterBagColor = outerMatcher.find();
    assert foundOuterBagColor : "could not find outer bag color for input \"" + input + "\"";
    var outerBagColor = outerMatcher.group("color");

    var innerBags = new HashMap<String, Integer>();
    var innerMatcher = INNER_BAGS_QTY_AND_COLOR.matcher(input);
    while (innerMatcher.find()) {
      innerBags.put(innerMatcher.group("color"), Integer.parseInt(innerMatcher.group("qty")));
    }

    return builder().outerBagColor(outerBagColor).innerBags(innerBags).build();
  }
}
