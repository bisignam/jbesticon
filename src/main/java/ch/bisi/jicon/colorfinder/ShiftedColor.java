package ch.bisi.jicon.colorfinder;

import java.awt.Color;

/**
 * Class representing a {@link Color} shifted by N pixels.
 */
public class ShiftedColor {

  private int shift;
  private Color color;

  public ShiftedColor(final int shift, final Color color) {
    this.shift = shift;
    this.color = color;
  }

  public int getShift() {
    return shift;
  }

  public Color getColor() {
    return color;
  }

  public int getRed() {
    return this.color.getRed();
  }

  public int getGreen() {
    return this.color.getGreen();
  }

  public int getBlue() {
    return this.getColor().getBlue();
  }

  @Override
  @SuppressWarnings("PMD.CyclomaticComplexity")
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    final ShiftedColor that = (ShiftedColor) other;

    if (shift != that.shift) {
      return false;
    }

    return color != null ? color.equals(that.color) : that.color == null;
  }

  @Override
  public int hashCode() {
    int result = shift;
    result = 31 * result + (color != null ? color.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ShiftedColor{"
        + "shift=" + shift
        + ", color=" + color
        + '}';
  }
}
