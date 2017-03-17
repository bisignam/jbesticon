package ch.bisi.jicon.colorfinder;

/**
 * Class representing an entry in the Map used to find the main color of an image.
 */
class ColorStats {

  private final int count;
  private final double weight;

  ColorStats(final int count, final double weight) {
    this.count = count;
    this.weight = weight;
  }

  int getCount() {
    return count;
  }

  double getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "ColorStats{"
        + "count=" + count
        + ", weight=" + weight
        + '}';
  }
}
