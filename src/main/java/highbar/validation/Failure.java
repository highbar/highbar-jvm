package highbar.validation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Provides a failed validation monad.
 * @param <X> represents the failure type.
 */
public class Failure<X> extends Validation<X> {
  private final Stream<String> _value;

  public Failure(String message) {
    _value = Stream.of(message);
  }

  public Failure(Stream<String> messages) {
    _value = messages;
  }

  @Override
  public Validation<X> ifSuccess(Consumer<X> action) {
    return this;
  }

  @Override
  public Validation<X> ifFailure(Consumer<X> action) {
    action.accept(null);
    return this;
  }

  @Override
  public X orElse(X value) {
    return value;
  }

  @Override
  public Validation<X> orElse(Consumer<Stream<String>> action) {
    action.accept(_value);
    return this;
  }

  @Override
  public X orElseGet(Supplier<? extends X> supplier) {
    return supplier.get();
  }

  @Override
  public  <T extends Throwable> void orElseThrow(Supplier<? extends T> action) throws T {
    throw action.get();
  }

  @Override
  public <Y> Validation<Y> chain(Function<X, Validation<Y>> chainer) {
    return Failure.<Y>of(_value);
  }

  @Override
  public Validation<X> concat(Validation<X> other) {
    return other.isSuccess() ?
      this :
      Failure.of(Stream.concat(_value, ((Failure<? extends X>) other)._value));
  }

  @Override
  public <Y> Validation<Y> map(Function<X, Y> mapper) {
    return Failure.<Y>of(_value);
  }

  @Override
  public boolean isSuccess() {
    return false;
  }

  public static <X> Failure<X> of(String message) {
    return new Failure<>(message);
  }

  public static <X> Failure<X> of(Stream<String> messages) {
    return new Failure<>(messages);
  }

  public static <X> Failure<X> of(String... messages) {
    return new Failure<>(Stream.of(messages));
  }
}
