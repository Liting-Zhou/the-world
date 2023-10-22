package model;

/**
 * An interface that provides the ability to generate truly random numbers
 * or predictable numbers based on the provided integer values.
 */
public interface Random {

  /**
   * Generates the next pseudorandom, uniformly distributed int value between 0 (inclusive)
   * and the specified bound (exclusive).
   *
   * @param bound the upper bound (exclusive). Must be positive.
   * @return the next pseudorandom, uniformly distributed int value between 0 (inclusive)
   * and bound (exclusive).
   * @throws IllegalArgumentException if the bound is not positive.
   */
  int nextRandomInt(int bound);
}
