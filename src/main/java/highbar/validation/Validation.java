package highbar.validation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Provides base functionality for validation monads.
 * @param <X> represents the validation type.
 */
public abstract class Validation<X> {
  /**
   * Determines whether or not the value is a {@link Failure}.
   *
   * @return {@code true} if the value is a {@link Failure}, otherwise {@code false}
   */
  public boolean isFailure() {
    return !isSuccess();
  }

  /**
   * Applies the provided function to the value contain for a {@link Success}. Any return value from the function is ignored.
   * If the instance is a {@link Failure}, the function is ignored and the instance is returned.
   *
   * @param action The function to invoke with the value
   *
   * @return Current instance.
   */
  public abstract Validation<X> ifSuccess(Consumer<X> action);

  /**
   * Applies the provided function to the value contain for a {@link Failure}. Any return value from the function is ignored.
   * If the instance is a {@link Success}, the function is ignored and the instance is returned.
   *
   * @param action The function to invoke with the value
   *
   * @return Current instance.
   */
  public abstract Validation<X> ifFailure(Consumer<X> action);

  /**
   * If the instance is a {@link Success}, returns the value of the instance.  If the instance is a {@link Failure},
   * return the provided value.
   *
   * @param value The value to provide if the instance is a {@link Failure).
   *
   * @return The value of the current instance.
   */
  public abstract X orElse(X value);

  /**
   * If the instance is a {@link Success}, returns the value of the instance.  If the instance is a {@link Failure},
   * applies the provided function to the value of the instance.
   *
   * @param action The function to invoke with the instance value.
   *
   * @return Current instance.
   */
  public abstract Validation<X> orElse(Consumer<Stream<String>> action);

  /**
   * If the instance is a {@link Success}, returns the value of the instance.  If the instance is a {@link Failure},
   * invokes the provided function and returns the computed value.
   *
   * @param supplier The function to invoke to get a value.
   *
   * @return The value of the current instance.
   */
  public abstract X orElseGet(Supplier<? extends X> supplier);

  /**
   * If the instance is a {@link Success}, returns the instance.  If the instance is a {@link Failure}, invokes the
   * provided function and throws the provided {@link Throwable}.
   *
   * @param supplier The function to invoke to get the error.
   * @param <T> The type of {@link Throwable} that will be returned from the provided function.
   *
   * @throws T
   */
  public abstract <T extends Throwable> void orElseThrow(Supplier<? extends T> supplier) throws T;

  /**
   * Applies the provided function to the value contained for a {@link Success}. The function should return the value
   * wrapped in a {@link Validation}. If the instance is a {@link Failure}, the function is ignored and then instance is
   * returned unchanged.
   *
   * @param chainer The function to invoke with the value.
   * @param <Y> The type contained in the {@link Validation}.
   *
   * @return {@link Validation} wrapped value returned by the provided {@ref chainer}.
   */
  public abstract <Y> Validation<Y> chain(Function<X, Validation<Y>> chainer);

  /**
   * Concatenates another {@link Validation} instance with the current instance.
   *
   * @param other Other {@link Validation} for concatenation.
   *
   * @return Concatenated validations.
   */
  public abstract Validation<X> concat(Validation<X> other);

  /**
   * Applies the provided function to the value contained for a {@link Success} which is, in turn, wrapped in a
   * {@link Success}. If the instance is a {@link Failure}, the function is ignored and then instance is returned
   * unchanged.
   *
   * @param mapper The function to invoke with the value.
   * @param <Y>
   *
   * @return {@link Validation} wrapped value mapped with the provided {@ref mapper}.
   */
  public abstract <Y> Validation<Y> map(Function<X, Y> mapper);

  /**
   * Determines whether or not the value is a {@link Success}.
   *
   * @return {@code true} if the value is a {@link Success}, otherwise {@code false}
   */
  public abstract boolean isSuccess();
}
