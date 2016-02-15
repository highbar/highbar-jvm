package highbar.function;

/**
 * Defines a supplier that throws one or more exceptions, thereby making it unsuitable for use in lambdas and
 * streaming APIs.
 */
@FunctionalInterface
public interface UnstreamableSupplier<T> {
  /**
   * Gets a result.
   *
   * @return a result
   */
  T get();
}
