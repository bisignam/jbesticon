package ch.bisi.jicon.colorfinder;

import static java.lang.StrictMath.abs;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * {@link ColorFinder} implementation used by the Jicon library.
 */
public class JiconColorFinder extends ColorFinder {

  /**
   * Instantiates a new {@link JiconColorFinder}.
   *
   * @param image the {@link BufferedImage} to process
   */
  JiconColorFinder(final BufferedImage image) {
    super(image);
  }

  /**
   * Implements the {@link JiconColorFinder} weighing strategy.
   *
   * @param color the {@link Color}
   * @return the weight of the given {@link Color}.
   */
  @Override
  public double getWeight(final Color color) {
    final int red = color.getRed();
    final int green = color.getGreen();
    final int blue = color.getBlue();
    final double weight =
        (abs(red - green) * abs(red - green)
            + abs(red - blue) * abs(red - blue)
            + abs(green - blue) * abs(green - blue))
            / 65535.0d * 1000.0d + 1.0d;
    if (weight <= 0) {
      return 1E-10d;
    }
    return weight;
  }

}
