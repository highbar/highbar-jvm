package highbar.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Provides wrappers for unstreamable methods (methods that use checked exceptions), making them suitable for use with Java
 * 8 Streams.  Standard exceptions are wrapped with RuntimeException to remove the check requirement. Use with caution.
 */
public class Streamable {
  /**
   * Wraps a unstreamable Runnable so that it doesn't throw any checked exceptions.
   * @param runnable The unstreamable Runnable to wrap.
   * @return A Runnable that doesn't throw any checked exceptions.
   */
  public static Runnable runnable(UnstreamableRunnable runnable) {
    return () -> safeRun(input -> {
      runnable.run();
      return null;
    }, null);
  }

  /**
   * Wraps a unstreamable Consumer so that it doesn't throw any checked exceptions.
   * @param consumer The unstreamable Consumer to wrap.
   * @param <T> The type of the input to the operation.
   * @return A Consumer that doesn't throw any checked exceptions.
   */
  public static <T> Consumer<T> consumer(UnstreamableConsumer<T> consumer) {
    return data -> safeRun(input -> {
      consumer.accept(input);
      return null;
    }, data);
  }

  /**
   * Wraps a unstreamable Function so that it doesn't throw any checked exceptions.
   * @param function The unstreamable Function to wrap.
   * @param <I> The type of the input to the Function.
   * @param <O> The type of the result of the Function.
   * @return A Function that doesn't throw any checked exceptions.
   */
  public static <I, O> Function<I, O> function(UnstreamableFunction<I, O> function) {
    return input -> safeRun(function, input);
  }

  /**
   * Wraps a unstreamable Predicate so that it doesn't throw any checked exceptions.
   * @param predicate The unstreamable Predicate to wrap.
   * @param <T> The type of the input argument to the Predicate.
   * @return A Predicate that doesn't throw any checked exceptions.
   */
  public static <T> Predicate<T> predicate(UnstreamablePredicate<T> predicate) {
    return input -> safeRun(i -> {
      return predicate.test(i);
    }, input);
  }

  /**
   * Wraps a unstreamable Supplier so that it doesn't throw any checked exceptions.
   * @param supplier The unstreamable Supplier to wrap.
   * @param <T> The type of arguments supplied by this supplier.
   * @return A Supplier that doesn't throw any checked exceptions.
   */
  public static <T> Supplier<T> supplier(UnstreamableSupplier<T> supplier) {
    return () -> safeRun(input -> {
      return supplier.get();
    }, null);
  }

  private static <I,O> O safeRun(UnstreamableFunction<I,O> function, I input) {
    try {
      return function.apply(input);
    } catch (Error | RuntimeException error) {
      throw error;
    } catch (Throwable error) {
      throw new RuntimeException(error);
    }
  }
}
