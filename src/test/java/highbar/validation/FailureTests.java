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
 * Tests of the {@link Failure} class.
 */
@RunWith(HierarchicalContextRunner.class)
@SuppressWarnings(value = {"checkstyle:javadoctype", "unchecked"})
public class FailureTests {
  private Failure<String> _failure = Failure.of("success!");
  private Consumer<String> _consumer = mock(Consumer.class);

  public class DescribeIsSuccess {
    @Test
    public void itShouldReturnFalse() {
      Assert.assertFalse(_failure.isSuccess());
    }
  }

  public class DescribeIsFailure {
    @Test
    public void itShouldReturnTrue() {
      Assert.assertTrue(_failure.isFailure());
    }
  }

  public class DescribeIfSuccess {
    @Test
    public void itShouldNotCallTheProvidedConsumer() {
      _failure.ifSuccess(_consumer);

      verify(_consumer, never()).accept(_failure.orElse(""));
    }
  }

  public class DescribeIfFailure {
    @Test
    public void itShouldCallTheProvidedConsumer() {
      _failure.ifFailure(_consumer);

      verify(_consumer).accept(_failure.orElse((String)null));
    }
  }

  public class DescribeOrElseGet {
    private Supplier<String> _stringSupplier = mock(Supplier.class);

    @Test
    public void itShouldCallTheProvidedSupplier() {
      _failure.orElseGet(_stringSupplier);

      verify(_stringSupplier).get();
    }
  }

  public class DescribeOrElseThrow {
    private class TestInvocationException extends Exception {}

    @Test(expected = TestInvocationException.class)
    public void itShouldCallTheProvidedSupplier() throws Exception {
      _failure.orElseThrow(() -> new TestInvocationException());
    }
  }

  public class DescribeMap {
    private Function<String, Integer> _mapper = mock(Function.class);

    @Test
    public void itShouldNotCallTheProvidedFunction() {
      _failure.map(_mapper);

      verify(_mapper, never()).apply(_failure.orElse(""));
    }
  }

  public class DescribeChain {
    private Function<String, Validation<Integer>> _chainer = mock(Function.class);

    @Test
    public void itShouldNotCallTheProvidedFunction() {
      _failure.chain(_chainer);

      verify(_chainer, never()).apply(_failure.orElse(""));
    }
  }

  public class DescribeConcat {
    @Test
    public void itShouldReturnAFailureWhenGivenASuccess() {
      Assert.assertTrue(_failure.concat(Success.of("success!")).getClass() == Failure.class);
    }

    @Test
    public void itShouldConcatenateFailureValues() {
      _failure.concat(Failure.of("failure!")).orElse(stream -> Assert.assertEquals(2, stream.count()));
    }
  }
}
