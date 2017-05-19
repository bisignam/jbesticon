package ch.bisi.jicon.common;

import java.awt.Color;

/**
 * Utilities for manipulating colors.
 */
public class ColorUtil {

  private ColorUtil() {
    //hides public constructor
  }

  /**
   * Returns a proper foreground color with enough contrast ratio given a background {@link Color}.
   *
   * @param backgroundColor the background {@link Color}
   * @return the foreground {@link Color}.
   */
  public static Color getForegroundColor(final Color backgroundColor) {
    final Double contrastRatio = getWhiteContrastRatio(backgroundColor);
    if (contrastRatio > 1.5) {
      return Color.WHITE;
    }
    return Color.DARK_GRAY;
  }

  /**
   * Gets the contrast ratio between {@code Color.WHITE} and the given background {@link Color}.
   * Contrast ratios can range from 1 to 21.
   *
   * @param backgroundColor the background {@link Color}
   * @return the contrast ratio as a {@link Double}.
   * @see <a href="https://www.w3.org/TR/2008/REC-WCAG20-20081211/#contrast-ratiodef">Contrast
   * ratio</a>
   */
  private static Double getWhiteContrastRatio(final Color backgroundColor) {
    return (getRelativeLuminance(Color.WHITE) + 0.05) / (getRelativeLuminance(backgroundColor)
        + 0.05);
  }

  /**
   * Gets the relative brightness of any point in a colorspace,
   * normalized to 0 for darkest black and 1 for lightest white.
   *
   * @param color the {@link Color} for which to compute the relative luminance
   * @return the relative luminance
   * @see <a href="https://www.w3.org/TR/2008/REC-WCAG20-20081211/#relativeluminancedef">Relative
   * luminance</a>
   */
  private static Double getRelativeLuminance(final Color color) {
    return 0.2126d * (double) color.getRed() + 0.7152d * (double) color.getGreen()
        + 0.0722d * (double) color.getBlue();
  }


}
