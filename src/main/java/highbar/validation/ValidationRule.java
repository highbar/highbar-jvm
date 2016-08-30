package highbar.validation;

import java.util.function.Function;

/**
 * Defines a single validation rule.
 * @param <X> represents the rule type.
 */
public class ValidationRule<X> {
  private final Function<X, Boolean> _condition;
  private final Function<X, String> _message;
  private final String _name = null;

  public ValidationRule(Function<X, Boolean> condition, Function<X, String> message) {
    _condition = condition;
    _message = message;
  }

  public Validation<X> validate(X value) {
    return _condition.apply(value) ?
      Success.of(value) :
      Failure.of(_message.apply(value));
  }
}
