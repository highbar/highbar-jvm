package highbar.function;

/**
 * Defines a runnable that masks all checked exceptions, allowing it to be used in lambdas and streaming APIs.
 */
@FunctionalInterface
public interface UnstreamableRunnable {
  /**
   * Runs this function.
   * @throws Exception
   */
  void run() throws Exception;
}
