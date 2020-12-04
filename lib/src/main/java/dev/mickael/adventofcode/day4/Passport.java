package dev.mickael.adventofcode.day4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Passport {

  private static final Set<String> REQUIRED_FIELDS =
      Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

  private final Map<String, String> fields;

  public static Passport fromString(String input) {
    Map<String, String> fields = new HashMap<>();

    Arrays.stream(input.split("\\s"))
        .map(dat -> dat.split(":"))
        .forEach(dat -> fields.put(dat[0], dat[1]));

    return Passport.builder().fields(fields).build();
  }

  public boolean isValid() {
    return fields.keySet().containsAll(REQUIRED_FIELDS);
  }
}
