package model;

import java.util.Random;

/**
 * Encapsulates the Random class and provides the ability to generate truly random numbers
 * or predictable numbers based on the provided integer values.
 */
public class RandomNumber implements model.Random {
  private Random random;
  private int[] predictableValues;
  private int index;

  /**
   * Constructs an instance that depends upon Java's Random class whenever
   * a random number is needed.
   */
  public RandomNumber() {
    random = new Random();
    predictableValues = null;
    index = 0;
  }

  /**
   * Constructs an instance using the provided set of integer values
   * for generating predictable numbers.
   *
   * @param predictableValues The set of integer values that will be returned in order.
   */
  public RandomNumber(int... predictableValues) {
    this.random = null;
    this.predictableValues = predictableValues;
    this.index = 0;
  }

  /**
   * Generates the next pseudorandom, uniformly distributed int value between 0 (inclusive)
   * and the specified bound (exclusive).
   *
   * @param bound the upper bound (exclusive). Must be positive.
   * @return the next pseudorandom, uniformly distributed int value between 0 (inclusive)
   * and bound (exclusive).
   * @throws IllegalArgumentException if the bound is not positive.
   */
  @Override
  public int nextRandomInt(int bound) {
    if (bound <= 0) {
      throw new IllegalArgumentException("Bound must be positive.");
    }
    if (predictableValues == null) {
      return random.nextInt(bound);
    } else {
      int value = predictableValues[index];
      index = (index + 1) % predictableValues.length;
      return value;
    }
  }
}
