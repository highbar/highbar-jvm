package highbar.validation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Provides a successful validation monad.
 * @param <X> represents the success type.
 */
public class Success<X> extends Validation<X> {
  private final X _value;

  public Success(X value) {
    _value = value;
  }

  @Override
  public Validation<X> ifSuccess(Consumer<X> action) {
    action.accept(_value);
    return this;
  }

  @Override
  public Validation<X> ifFailure(Consumer<X> action) {
    return this;
  }

  @Override
  public X orElse(X value) {
    return _value;
  }

  @Override
  public Validation orElse(Consumer<Stream<String>> streamConsumer) { return this; }

  @Override
  public X orElseGet(Supplier<? extends X> supplier) {
    return _value;
  }

  @Override
  public  <T extends Throwable> void orElseThrow(Supplier<? extends T> supplier) throws T {}

  @Override
  public <Y> Validation<Y> chain(Function<X, Validation<Y>> chainer) {
    return chainer.apply(_value);
  }

  @Override
  public Validation<X> concat(Validation<X> other) {
    return other.isSuccess() ? this : other;
  }

  @Override
  public <Y> Validation<Y> map(Function<X, Y> mapper) {
    return Success.of(mapper.apply(_value));
  }

  @Override
  public boolean isSuccess() {
    return true;
  }

  public static <X> Success<X> of(X value) {
    return new Success<>(value);
  }
}
