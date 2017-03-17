package ch.bisi.jicon.common;

import java.awt.Dimension;

/**
 * Class representing an image embedded into a {@link JiconIcon}.
 **/
public class JiconIconImage {

  private final String format;
  private final Dimension dimension;

  /**
   * Instantiates a new {@link JiconIconImage}.
   *
   * @param format the format of the image
   * @param dimension the {@link Dimension} of the image
   */
  JiconIconImage(final String format, final Dimension dimension) {
    super();
    this.format = format;
    this.dimension = dimension;
  }

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  public double getWidth() {
    return dimension.getWidth();
  }

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  public double getHeight() {
    return dimension.getHeight();
  }

  /**
   * Gets a {@link String} representation of the format.
   *
   * @return the format
   */
  public String getFormat() {
    return format;
  }

  @Override
  public String toString() {
    return "JiconIconImage{"
        + ", format='" + format
        + ", dimension=" + dimension
        + '}';
  }
}
