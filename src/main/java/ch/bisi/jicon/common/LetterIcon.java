package ch.bisi.jicon.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

/**
 * Class representing a circle icon with a single color background and single letter written in the
 * middle of the image.
 */
public class LetterIcon {

  public static final float FONT_SIZE_FACTOR = 0.6880340f;
  private static final float Y_OFFSET_FACTOR = 102.0f / 1024.0f;

  /**
   * Width and height of the {@link LetterIcon}.
   */
  private final Integer size;

  /**
   * The letter written in the middle of the {@link LetterIcon}.
   */
  private final char letter;
  /**
   * The background {@link Color} of the {@link LetterIcon}.
   */
  private final Color backgroundColor;

  /**
   * Builds a {@link LetterIcon}.
   *
   * @param size the height and width of the {@link LetterIcon}.
   * @param letter the letter to write
   * @param backgroundColor the background color
   */
  public LetterIcon(final Integer size, final char letter, final Color backgroundColor) {
    this.size = size;
    this.letter = Character.toUpperCase(letter);
    this.backgroundColor = backgroundColor;
  }

  public Integer getSize() {
    return size;
  }

  public char getLetter() {
    return letter;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Gets a {@link BufferedImage} representing this {@link LetterIcon}.
   *
   * @return a {@link BufferedImage} representing the lettericon.
   */
  public BufferedImage getImage() {
    final BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    final Graphics2D graphics = image.createGraphics();
    graphics.setColor(backgroundColor);
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.fill(new Ellipse2D.Float(0, 0, size, size));
    writeLetter(letter, size, graphics);
    graphics.dispose();
    return image;
  }

  /**
   * Writes a letter on the given {@link Graphics2D}.
   *
   * @param letter the letter to write
   * @param size the size of the {@link Graphics2D}
   * @param graphics the {@link Graphics2D} on which to write the letter
   */
  private void writeLetter(final char letter, final Integer size,
      final Graphics2D graphics) {
    final float fontSize = FONT_SIZE_FACTOR * (float) size;
    final Font font = new Font("Arial", Font.PLAIN, size)
        .deriveFont(fontSize);
    graphics.setFont(font);
    final String letterToWrite = Character.toString(Character.toUpperCase(letter));
    final AttributedString stringToWrite = new AttributedString(letterToWrite);
    stringToWrite.addAttribute(TextAttribute.FONT, font);
    stringToWrite
        .addAttribute(TextAttribute.FOREGROUND,
            ColorUtil.getForegroundColor(graphics.getColor()));
    final int letterXPosition =
        (size - graphics.getFontMetrics().stringWidth(letterToWrite)) / 2;
    final int letterYPosition =
        (int) (Y_OFFSET_FACTOR * (float) (size)) + (int) Math.ceil(fontSize);
    graphics.drawString(stringToWrite.getIterator(), letterXPosition, letterYPosition);
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

    final LetterIcon that = (LetterIcon) other;

    if (letter != that.letter) {
      return false;
    }
    if (size != null ? !size.equals(that.size) : that.size != null) {
      return false;
    }
    return backgroundColor != null ? backgroundColor.equals(that.backgroundColor)
        : that.backgroundColor == null;
  }

  @Override
  public int hashCode() {
    int result = size != null ? size.hashCode() : 0;
    result = 31 * result + (int) letter;
    result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
    return result;
  }

}
