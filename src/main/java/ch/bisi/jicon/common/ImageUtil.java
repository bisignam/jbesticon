package ch.bisi.jicon.common;

import static ch.bisi.jicon.common.Util.replaceExtension;

import ch.bisi.jicon.ColorUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Ellipse2D;
import java.text.AttributedString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Utility class for reading and writing {@link Image}s.
 */
public class ImageUtil {

  private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

  private ImageUtil() {
    //hide public constructor
  }

  /**
   * Creates a lettericon with the given attributes.
   *
   * @param backgroundColor the background {@link Color} of the lettericon to create.
   * @param letter the letter to write.
   * @param size the size of the lettericon.
   * @return a {@link BufferedImage} representing the lettericon.
   */
  public static BufferedImage createLetterIcon(final Color backgroundColor, final char letter,
      final Integer size) {
    final float fontSizeFactor = 0.6880340f;
    final float yOffsetFactor = 102.0f / 1024.0f;
    final String letterToWrite = Character.toString(Character.toUpperCase(letter));
    final BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    final Graphics2D graphics = image.createGraphics();
    graphics.setColor(backgroundColor);
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.fill(new Ellipse2D.Float(0, 0, size, size));
    final float fontSize = fontSizeFactor * (float) size;
    final Font font = new Font("Arial", Font.PLAIN, size).deriveFont(fontSize);
    graphics.setFont(font);
    final AttributedString stringToWrite = new AttributedString(letterToWrite);
    stringToWrite.addAttribute(TextAttribute.FONT, font);
    stringToWrite
        .addAttribute(TextAttribute.FOREGROUND, ColorUtil.getForegroundColor(backgroundColor));
    final int xPosition =
        (size - graphics.getFontMetrics().stringWidth(letterToWrite)) / 2;
    final int yPosition = (int) (yOffsetFactor * (float) (size)) + (int) Math.ceil(fontSize);
    graphics.drawString(stringToWrite.getIterator(), xPosition, yPosition);
    graphics.dispose();
    return image;
  }

  /**
   * Generic method for executing a given operation for each image contained in the file at the
   * given {@link URL}. Results of the executed operations are collected and returned by the
   * method. Results are ordered as the images embedded in the file at the given {@link URL}.
   *
   * @param <T> the result of each executed operation
   * @param imageUrl the {@link URL} of the image resource
   * @param operation the {@link ThrowableBiFunction} to execute for each image contained in the
   *        file at the given {@link URL}
   * @return a {@link List} of {@link T}s containing each result returned by the executed operations
   * @throws ImageFormatNotSupportedException if no registered {@link ImageReader} has been found
   *         for the image at the given {@link URL}
   * @throws IOException if an error occurs retrieving the {@link ImageReader}
   *         or executing the input {@code operation}.
   */
  public static <T> List<T> executeOperationForEachEmbeddedImage(final URL imageUrl,
      final ThrowableBiFunction<ImageReader, Integer, T, IOException> operation)
      throws ImageFormatNotSupportedException, IOException {

    try (ImageInputStream imageStream = ImageIO.createImageInputStream(imageUrl.openStream())) {
      final ImageReader reader = getImageReader(imageUrl, imageStream)
          .orElseThrow(() -> new ImageFormatNotSupportedException(
              "Image format " + Util.getExtension(imageUrl.toString()) + " not supported"));

      final List<T> images = new ArrayList<>();
      try {
        final int imagesNum = reader.getNumImages(false);
        logger.trace("File at URL {} contains {} embedded images", imageUrl, imagesNum);
        for (int i = reader.getMinIndex(); i < imagesNum; i++) {
          logger.trace("Processing image number {}", i);
          images.add(operation.apply(reader, i));
        }
        return images;
      } finally {
        reader.dispose();
      }
    }
  }

  /**
   * Writes a {@link List} of {@link BufferedImage}s to files.
   *
   * @param images the {@link List} of {@link BufferedImage}s to save
   * @param imagesFormats the {@link List} of formats for each input {@link BufferedImage}
   * @param outputFilesPaths the {@link List} of paths where to store each
   *        input {@link BufferedImage}
   *
   * @throws IOException in case of problems writing the images to files
   */
  public static void writeImagesToFiles(final List<BufferedImage> images,
                                        final List<String> imagesFormats,
                                        final List<String> outputFilesPaths) throws IOException {
    if (!(outputFilesPaths.size() == imagesFormats.size()
        && imagesFormats.size() == images.size())) {
      throw new IllegalArgumentException(
          "All the input lists must have the same length");
    }
    for (int j = 0; j < images.size(); j++) {
      final BufferedImage image = images.get(j);
      final String imageFormat = imagesFormats.get(j);
      final String outputFilePath = outputFilesPaths.get(j);
      logger.trace("Saving BufferedImage at index {} and format {} in file {}", j,
          imageFormat, outputFilesPaths.get(j));
      final boolean write = writeImageToFile(image, imageFormat, outputFilePath);
      logger.trace("BufferedImage at index {} write result: {}", j, write);
    }
  }

  /**
   * Writes a {@link BufferedImage} to file.
   *
   * @param image the {@link BufferedImage} to save
   * @param imageFormat the format of the {@link BufferedImage}
   * @param outputFilePath the path of the file where to write the {@link BufferedImage}
   * @return {@code true} if the image has been written to file, {@code false} otherwise
   * @throws IOException in case of problems writing the image to file
   */
  private static boolean writeImageToFile(final BufferedImage image,
      final String imageFormat, final String outputFilePath) throws IOException {
    final File outputFile = new File(outputFilePath);
    boolean write;
    if (imageFormat.equals("ico")) {
      write = writeIcoDirectoryEntry(image, outputFilePath);
    } else {
      write = ImageIO
          .write(image, imageFormat, outputFile);
    }
    return write;
  }

  /**
   * Tries to write an image embedded into an .ico file (technically called {@code ICONDIRENTRY})
   * into its own separate file. Since each {@code ICONDIRENTRY} can represent both a PNG or a BMP
   * image the method tries to store the embedded image using both formats before returning false.
   *
   * @return {@code true} if the image has been written, {@code false} otherwise
   * @see <a href="http://en.wikipedia.org/wiki/ICO_(icon_image_file_format)">ICO file format
   * (Wikipedia)</a>
   */
  private static boolean writeIcoDirectoryEntry(final BufferedImage image,
      final String outputFilePath) throws IOException {
    boolean written = ImageIO
        .write(image, "png", new File(replaceExtension(outputFilePath, "png")));
    if (!written) {
      written = ImageIO.write(image, "bmp", new File(replaceExtension(outputFilePath, "bmp")));
    }
    return written;
  }

  /**
   * Returns the first valid {@link ImageReader} for the file at the given {@link URL}
   * and {@link ImageInputStream}.
   *
   * @param imageUrl the {@link URL} of the file containing the image(s)
   * @param imageStream the {@link ImageInputStream} for reading the image
   * @return an {@link Optional} {@link ImageReader}
   */
  private static Optional<ImageReader> getImageReader(final URL imageUrl,
      final ImageInputStream imageStream) {
    final String extension = Util.getExtension(imageUrl.toString());
    Iterator<ImageReader> imageReaders;
    imageReaders = ImageIO.getImageReaders(imageStream);
    if (!imageReaders.hasNext() && extension != null) {
      imageReaders = ImageIO.getImageReadersBySuffix(extension);
    }
    if (!imageReaders.hasNext()) {
      return Optional.empty();
    }
    final ImageReader reader = imageReaders.next();
    reader.setInput(imageStream);
    return Optional.of(reader);
  }

}
