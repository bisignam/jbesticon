package ch.bisi.jbesticon.colorfinder;

import static java.lang.StrictMath.floor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * Utility class for finding the main color of a given image.
 * This implementation is a port of https://github.com/pieroxy/color-finder.
 */
abstract class ColorFinder {

  /**
   * If the input image size is over this threshold the algorithm starts undersampling.
   */
  private static final int SAMPLE_THRESHOLD = 180 * 180;

  /**
   * The {@link BufferedImage} to process.
   */
  private final BufferedImage image;

  /**
   * Instantiates a new {@link ColorFinder}.
   *
   * @param image the {@link BufferedImage} to process
   */
  ColorFinder(final BufferedImage image) {
    this.image = image;
  }

  /**
   * Finds the main color of the {@link BufferedImage} passed on construction.
   *
   * @return the main {@link Color} of the image
   */
  Color findMainColor() throws EmptyImageException {
    final Map<Color, ColorStats> colorStatsMap = buildColorMap(image);
    ShiftedColor result = findMainColorStep(image, colorStatsMap, 6, null);
    result = findMainColorStep(image, colorStatsMap, 4, result);
    result = findMainColorStep(image, colorStatsMap, 2, result);
    result = findMainColorStep(image, colorStatsMap, 0, result);
    return result.getColor();
  }


  /**
   * Builds the {@link Color}s statistics map from a {@link BufferedImage}. The created {@link Map}
   * of statics associates to each {@link Color} in the image a {@link ColorStats}. The weight
   * stored in each {@link ColorStats} is computed using the {@link #getWeight(Color)} method. The
   * method is used to build the initial set of statics given as input to the color finding
   * algorithm.
   *
   * @param image the {@link BufferedImage} to process
   * @return the {@link Map} of {@link ColorStats} extracted from the input {@link BufferedImage}
   */
  private Map<Color, ColorStats> buildColorMap(final BufferedImage image) {
    final Map<Color, ColorStats> colorStats = new HashMap<>();
    for (int y = image.getMinY(); y < image.getHeight(); y++) {
      for (int x = image.getMinX(); x < image.getWidth(); x++) {
        final Color color = getPixelColor(image, x, y);
        colorStats.merge(color, new ColorStats(1, getWeight(color)),
            (stat1, stat2) -> new ColorStats(stat1.getCount() + 1, stat1.getWeight()));
      }
    }
    return colorStats;
  }
  
  // @formatter:off
  /**
   * Executes a step of of the color finding algorithm.
   *
   * @implSpec Processes a {@link BufferedImage} in the following way:
   *           <ul>
   *            <li>Determines the sampling step</li>
   *            <li>Loop over the {@code image} pixels by using the computed sampling step</li>
   *            <li>At each sampling step:</li>
   *            <ul>
   *              <li>Retrieves the {@link Color} of the processed pixel</li>
   *              <li>If the retrieved {@link Color} matches the {@code targetColor} or if
   *                  the {@code targetColor} is {@code null},
   *                  increases the weight of the given Color</li>
   *            </ul>
   *            <li>Inspects the built {@link Map} of weights and
   *                returns the {@link ShiftedColor} with the maximum weight</li>
   *           </ul>
   *
   * @param image the {@link BufferedImage} to process
   * @param colorStatsMap the initial set of statistics storing frequency and weight
   *                      for each {@link Color} of the {@code image}.
   * @param shift right shift to apply to image {@link Color}s RGB values.
   * @param targetColor the target color to match or {@code null} if there is no
   * @return the {@link ShiftedColor} which has the maximum weight at the end of the process
   * @throws EmptyImageException if the input {@code image} is empty
   */
  // @formatter:on
  private ShiftedColor findMainColorStep(final BufferedImage image,
      final Map<Color, ColorStats> colorStatsMap,
      final int shift, final ShiftedColor targetColor) throws EmptyImageException {
    final Map<ShiftedColor, Double> weightedColors = new HashMap<>();
    final int stepLength = getStepLength(image);
    for (int y = image.getMinY(); y < image.getHeight(); y += stepLength) {
      for (int x = image.getMinX(); x < image.getWidth(); x += stepLength) {
        final Color color = getPixelColor(image, x, y);
        if (matchTargetColor(targetColor, color)) {
          increaseColorWeight(weightedColors, colorStatsMap, color, shift);
        }
      }
    }
    return getColorWithMaxWeightOrThrow(image, weightedColors);
  }


  /**
   * Gets the {@link ShiftedColor} with the maximum weight from {@code weightedColors}.
   *
   * @param image the {@link BufferedImage} being processed
   * @param weightedColors the {@link Map} of weighted {@link ShiftedColor} to process.
   * @return the {@link ShiftedColor} with the maximum weight in the input map
   * @throws EmptyImageException in case {@code weightedColors} is empty
   */
  private ShiftedColor getColorWithMaxWeightOrThrow(final BufferedImage image,
      final Map<ShiftedColor, Double> weightedColors) throws EmptyImageException {
    final Optional<ShiftedColor> winningColor = getColorWithMaxWeight(weightedColors);
    return winningColor.orElseThrow(() -> new EmptyImageException("Image " + image + " is empty"));
  }

  /**
   * Gets the {@link ShiftedColor} with the maxium weight from {@code weightedColors}.
   *
   * @param weightedColors the {@link Map} of weighted {@link ShiftedColor} to process.
   * @return the {@link Optional} {@link ShiftedColor} with the maximum weight in the input map or
   * {@link Optional#empty()} if {@code weightedColors} is empty.
   */
  private Optional<ShiftedColor> getColorWithMaxWeight(
      final Map<ShiftedColor, Double> weightedColors) {
    final Optional<Entry<ShiftedColor, Double>> winningColor = weightedColors.entrySet().stream()
        .max(Comparator.comparingDouble(Entry::getValue));
    if (!winningColor.isPresent()) {
      return Optional.empty();
    }
    return Optional.of(winningColor.get().getKey());
  }

  /**
   * Retrieves the sampling step.
   *
   * @param image the {@link BufferedImage} to process
   * @return the sampling step
   */
  private int getStepLength(final BufferedImage image) {
    final int pixelCount = image.getWidth() * image.getHeight();
    if (pixelCount > SAMPLE_THRESHOLD) {
      return 2;
    }
    return 1;
  }

  /**
   * Gets the {@link Color} of a given pixel of an image.
   *
   * @param image the {@link BufferedImage} from which to extract the color
   * @param xcoord x coordinate of the pixel
   * @param ycoord coordinate of the pixel
   * @return the {@link Color} at the given coordinates
   */
  private Color getPixelColor(final BufferedImage image, final int xcoord, final int ycoord) {
    return new Color(image.getRGB(xcoord, ycoord));
  }

  /**
   * Gets the weight of a given {@link Color}.
   *
   * @param color the {@link Color}
   * @return the weight of the given {@link Color}.
   */
  abstract double getWeight(final Color color);

  /**
   * Checks if a given color matches another target color.
   *
   * @param targetColor the {@link Color} to match
   * @param color the {@link Color} to match against the target
   * @return true if they match or if {@code targetColor} is {@code null}, false otherwise
   */
  private boolean matchTargetColor(final ShiftedColor targetColor, final Color color) {
    return targetColor == null || targetColor.getRed() == (color.getRed() >> targetColor.getShift())
        && targetColor.getGreen() == (color.getGreen() >> targetColor.getShift())
        && targetColor.getBlue() == (color.getBlue() >> targetColor.getShift());
  }

  /**
   * Increases the weight of a given {@link Color} in the weighted colors {@link Map}.
   *
   * @param weightedColors the {@link Map} of weighted colors to update
   * @param colorStatsMap the {@link Color}s {@link Map} containing {@link Color}s statistics
   * @param color the {@link Color} entry to update
   * @param shift the shifting of the colors in the weighed colors {@link Map}
   */
  private void increaseColorWeight(final Map<ShiftedColor, Double> weightedColors,
      final Map<Color, ColorStats> colorStatsMap, final Color color, final int shift) {
    final ShiftedColor pixelGroup = getShiftedColor(color, shift);
    final ColorStats colorStats = colorStatsMap.get(color);
    weightedColors
        .compute(pixelGroup, (key, value) -> {
          final double toAdd =
              floor(colorStats.getWeight() * colorStats.getCount() * 100.0) / 100.0;
          if (value == null) {
            return toAdd;
          }
          return value + toAdd;
        });
  }

  /**
   * Builds a {@link ShiftedColor} instance.
   *
   * @param color the {@link Color}
   * @param shift the shift to apply to the input {@link Color}
   * @return an instance of {@link ShiftedColor} containing the shift value and a {@link Color} with
   *         right shifted RGB values.
   */
  private ShiftedColor getShiftedColor(final Color color, final int shift) {
    return new ShiftedColor(shift,
        new Color(color.getRed() >> shift,
            color.getGreen() >> shift,
            color.getBlue() >> shift));
  }

}
