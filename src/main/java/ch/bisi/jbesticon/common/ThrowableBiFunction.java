package ch.bisi.jbesticon.common;

/**
 * Represents a function that accepts two arguments, produces a result and may throw an {@link
 * Exception}.
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R, E extends Exception> {

  R apply(T input1, U input2) throws E;

}
