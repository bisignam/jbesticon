package ch.bisi.jicon.common;

/**
 * Represents a function that accepts two arguments, produces a result and may throw an {@link
 * Exception}.
 *
 * @param <T> The type of the first input parameter
 * @param <U> The type of the second input parameter
 * @param <R> The type of the result
 * @param <E> The type of the {@link Exception} which might be thrown by the function
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, E extends Exception> {

  /**
   * Applies the the {@link ThrowableBiFunction}.
   *
   * @param input1 the first input parameter of type {@link T}
   * @param input2 the second input parameter of type {@link U}
   * @return a result of type {@link R}
   * @throws E an instance of {@link Exception}
   */
  R apply(T input1, U input2) throws E;

}
