package highbar.function;

/**
 * Defines a runnable that throws one or more exceptions, thereby making it unsuitable for use in lambdas and
 * streaming APIs.
 */
@FunctionalInterface
public interface UnstreamableRunnable {
  /**
   * Runs this function.
   * @throws Exception
   */
  void run() throws Exception;
}
