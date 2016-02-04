package highbar.function;

import java.util.Objects;

/**
 * Defines a predicate that masks all checked exceptions, allowing it to be used in lambdas and streaming APIs.
 */
@FunctionalInterface
public interface UnstreamablePredicate<T> {
  /**
   * Evaluates this Predicate on the given argument.
   *
   * @param input the input argument
   * @return true if the input argument matches the predicate, otherwise false
   * @throws Exception
   */
  boolean test(T input) throws Exception;

  /**
   * Returns a composed predicate that represents a short-circuiting logical
   * AND of this predicate and another.  When evaluating the composed
   * predicate, if this predicate is {@code false}, then the {@code other}
   * predicate is not evaluated.
   *
   * <p>Any exceptions thrown during evaluation of either predicate are relayed
   * to the caller; if evaluation of this predicate throws an exception, the
   * {@code other} predicate will not be evaluated.
   *
   * @param other a predicate that will be logically-ANDed with this
   *              predicate
   * @return a composed predicate that represents the short-circuiting logical
   * AND of this predicate and the {@code other} predicate
   * @throws NullPointerException if other is null
   */
  default UnstreamablePredicate<T> and(UnstreamablePredicate<? super T> other) {
    Objects.requireNonNull(other);

    return (T input) -> test(input) && other.test(input);
  }

  /**
   * Returns a predicate that represents the logical negation of this
   * predicate.
   *
   * @return a predicate that represents the logical negation of this
   * predicate
   */
  default UnstreamablePredicate<T> negate() {
    return (T input) -> !(test(input));
  }

  /**
   * Returns a composed predicate that represents a short-circuiting logical
   * OR of this predicate and another.  When evaluating the composed
   * predicate, if this predicate is {@code true}, then the {@code other}
   * predicate is not evaluated.
   *
   * <p>Any exceptions thrown during evaluation of either predicate are relayed
   * to the caller; if evaluation of this predicate throws an exception, the
   * {@code other} predicate will not be evaluated.
   *
   * @param other a predicate that will be logically-ORed with this
   *              predicate
   * @return a composed predicate that represents the short-circuiting logical
   * OR of this predicate and the {@code other} predicate
   * @throws NullPointerException if other is null
   */
  default UnstreamablePredicate<T> or(UnstreamablePredicate<T> other) {
    Objects.requireNonNull(other);

    return (T input) -> test(input) || other.test(input);
  }

  /**
   * Returns a predicate that tests if two arguments are equal according
   * to {@link Objects#equals(Object, Object)}.
   *
   * @param <T> the type of arguments to the predicate
   * @param targetRef the object reference with which to compare for equality,
   *               which may be {@code null}
   * @return a predicate that tests if two arguments are equal according
   * to {@link Objects#equals(Object, Object)}
   */
  static <T> UnstreamablePredicate<T> isEqual(Object targetRef) {
    return (targetRef == null)
      ? Objects::isNull
      : object -> targetRef.equals(object);
  }
}
