package dev.mickael.adventofcode.day4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StrictPassport {

  private static final Set<String> REQUIRED_FIELDS =
      Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

  private static final Set<String> VALID_EYE_COLORS =
      Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

  private static final Pattern HEIGHT_PATTERN =
      Pattern.compile("\\A(?<height>\\d+)(?<unit>cm|in)\\z");

  private static final Pattern HAIR_COLOR_PATTERN = Pattern.compile("\\A#[a-f0-9]{6}\\z");

  private static final Pattern PASSPORT_PATTERN = Pattern.compile("\\A\\d{9}\\z");

  private final Map<String, String> fields;

  public static StrictPassport fromString(String input) {
    Map<String, String> fields = new HashMap<>();

    Arrays.stream(input.split("\\s"))
        .map(dat -> dat.split(":"))
        .forEach(dat -> fields.put(dat[0], dat[1]));

    return StrictPassport.builder().fields(fields).build();
  }

  public boolean isValid() {
    var hasAllRequiredFields = fields.keySet().containsAll(REQUIRED_FIELDS);
    if (!hasAllRequiredFields) return false;

    return fields.entrySet().stream().allMatch(this::isValidEntry);
  }

  private boolean isValidEntry(Entry<String, String> entry) {
    var val = entry.getValue();
    var result = false;

    switch (entry.getKey()) {
      case "byr":
        result = isValidBirthYear(val);
        break;
      case "iyr":
        result = isValidIssueYear(val);
        break;
      case "eyr":
        result = isValidExpirationYear(val);
        break;
      case "hgt":
        result = isValidHeight(val);
        break;
      case "hcl":
        result = isValidHairColor(val);
        break;
      case "ecl":
        result = isValidEyeColor(val);
        break;
      case "pid":
        result = isValidPassportID(val);
        break;
      case "cid":
        result = true; // ignored, missing or not
        break;
    }

    return result;
  }

  private boolean isValidPassportID(String val) {
    return PASSPORT_PATTERN.matcher(val).matches();
  }

  private boolean isValidEyeColor(String val) {
    return VALID_EYE_COLORS.contains(val);
  }

  private boolean isValidHairColor(String val) {
    return HAIR_COLOR_PATTERN.matcher(val).matches();
  }

  private boolean isValidHeight(String val) {
    var m = HEIGHT_PATTERN.matcher(val);

    if (!m.matches()) return false;
    var height = Integer.parseInt(m.group("height"));

    switch (m.group("unit")) {
      case "cm":
        return height >= 150 && height <= 193;
      case "in":
        return height >= 59 && height <= 76;
    }

    return false;
  }

  private boolean isValidExpirationYear(String val) {
    var year = Integer.parseInt(val);

    return year >= 2020 && year <= 2030;
  }

  private boolean isValidIssueYear(String val) {
    var year = Integer.parseInt(val);

    return year >= 2010 && year <= 2020;
  }

  private boolean isValidBirthYear(String val) {
    var year = Integer.parseInt(val);

    return year >= 1920 && year <= 2002;
  }
}
