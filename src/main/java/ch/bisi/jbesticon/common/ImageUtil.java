package ch.bisi.jbesticon.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
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
 * Utility class for manipulating {@link Image}s.
 */
public class ImageUtil {

  private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

  private ImageUtil() {
    //hide public constructor
  }

  /**
   * Generic method for executing a given operation for each image contained in the file at the
   * given {@link URL}. Results of the executed operations are collected and returned by the
   * method.
   *
   * @param imageUrl the {@link URL} of the image resource
   * @param operation the {@link ThrowableBiFunction} to execute for each image contained in the
   *        file at the given {@link URL}
   * @param <T> the result of each executed operation
   * @return the list of each results returned by the executed operations
   * @throws ImageFormatNotSupportedException if no registered {@link ImageReader} has been found
   *         for the image at the given {@link URL}
   * @throws IOException if an error occurs executing the operations
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
   * Returns the first valid {@link ImageReader} for the file at the given {@link URL}
   * and {@link ImageInputStream}.
   *
   * @param imageUrl the {@link URL} of the file containing the image(s)
   * @param imageStream the {@link ImageInputStream} for reading the image
   * @return an {@link Optional} {@link ImageReader}
   * @throws IOException in case of problems reading the file
   */
  private static Optional<ImageReader> getImageReader(final URL imageUrl,
      final ImageInputStream imageStream) throws IOException {
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
