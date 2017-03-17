package ch.bisi.jbesticon.common;

import java.awt.Dimension;
import java.net.URL;

/**
 * Class representing an image embedded into a {@link JiconIcon}.
 **/
public class JiconIconImage {

  private final int imageIndex;
  private final String format;
  private final Dimension dimension;

  /**
   * Instantiates a new {@link JiconIconImage}.
   * @param imageIndex the image index for this image
   * @param format the format of the image
   * @param dimension the {@link Dimension} of the image
   */
  public JiconIconImage(final int imageIndex, final String format, final Dimension dimension) {
    super();
    this.imageIndex = imageIndex;
    this.format = format;
    this.dimension = dimension;
  }

  /**
   * Gets the image index.
   *
   * @return the {@link URL}
   */
  public int getImageIndex() {
    return imageIndex;
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
        + ", imageIndex=" + imageIndex
        + ", format='" + format
        + ", dimension=" + dimension
        + '}';
  }
}
