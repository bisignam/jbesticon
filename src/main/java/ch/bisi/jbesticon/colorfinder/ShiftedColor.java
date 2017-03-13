package ch.bisi.jbesticon.colorfinder;

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
}
