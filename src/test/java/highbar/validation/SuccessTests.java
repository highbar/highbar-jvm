package highbar.validation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests of the {@link Success} class.
 */
@RunWith(HierarchicalContextRunner.class)
@SuppressWarnings(value = { "checkstyle:javadoctype", "unchecked" })
public class SuccessTests {
  private Success<String> _success = Success.of("success!");
  private Consumer<String> _consumer = mock(Consumer.class);

  public class DescribeIsSuccess {
    @Test
    public void itShouldReturnTrue() {
      Assert.assertTrue(_success.isSuccess());
    }
  }

  public class DescribeIsFailure {
    @Test
    public void itShouldReturnFalse() {
      Assert.assertFalse(_success.isFailure());
    }
  }

  public class DescribeIfSuccess {
    @Test
    public void itShouldCallTheProvidedConsumer() {
      _success.ifSuccess(_consumer);

      verify(_consumer).accept(_success.orElse(""));
    }
  }

  public class DescribeIfFailure {
    @Test
    public void itShouldNotCallTheProvidedConsumer() {
      _success.ifFailure(_consumer);

      verify(_consumer, never()).accept(_success.orElse(""));
    }
  }

  public class DescribeOrElseGet {
    private Supplier<String> _stringSupplier = mock(Supplier.class);

    @Test
    public void itShouldNotCallTheProvidedSupplier() {
      _success.orElseGet(_stringSupplier);

      verify(_stringSupplier, never()).get();
    }
  }

  public class DescribeOrElseThrow {
    private Supplier<Exception> _exceptionSupplier = mock(Supplier.class);

    @Test
    public void itShouldNotCallTheProvidedSupplier() throws Exception {
      _success.orElseThrow(_exceptionSupplier);

      verify(_exceptionSupplier, never()).get();
    }
  }

  public class DescribeMap {
    private Function<String, Integer> _mapper = mock(Function.class);

    @Test
    public void itShouldCallTheProvidedFunction() {
      _success.map(_mapper);

      verify(_mapper).apply(_success.orElse(""));
    }
  }

  public class DescribeChain {
    private Function<String, Validation<Integer>> _chainer = mock(Function.class);

    @Test
    public void itShouldCallTheProvidedFunction() {
      _success.chain(_chainer);

      verify(_chainer).apply(_success.orElse(""));
    }
  }

  public class DescribeConcat {
    @Test
    public void itShouldReturnASuccessWhenGivenASuccess() {
      Assert.assertTrue(_success.concat(Success.of("other success!")).getClass() == Success.class);
    }

    @Test
    public void itShouldReturnAFailureWhenGivenAFailure() {
      Assert.assertTrue(_success.concat(Failure.of("failure!")).getClass() == Failure.class);
    }
  }
}
