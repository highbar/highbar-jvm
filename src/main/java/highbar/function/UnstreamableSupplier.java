package highbar.function;

/**
 * Defines a supplier that masks all checked exceptions, allowing it to be used in lambdas and streaming APIs.
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
