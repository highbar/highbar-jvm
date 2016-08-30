package highbar.validation;

import java.util.List;

/**
 * Deals with validations in aggregate.
 */
public class Validations {
  /**
   * Applies the provided value to every {@link ValidationRule} in the {@ref rules} collection and returns a
   * {@link Validation} with the result.
   *
   * @param value The value to be applied to the {@ref rules} collection.
   * @param rules The {@link ValidationRule}s to be run.
   * @param <X>
   *
   * @return A {@link Validation} containing all failures concatenated together, or success.
   */
  public static <X> Validation<X> all(X value, List<ValidationRule<X>> rules) {
    return rules.stream().map(rule -> rule.validate(value)).reduce(Success.of(value), Validation::concat);
  }
}
