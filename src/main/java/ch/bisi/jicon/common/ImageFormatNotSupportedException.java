package ch.bisi.jicon.common;

/**
 * Exception indicating that a given image format is not supported.
 */
public class ImageFormatNotSupportedException extends Exception {
  ImageFormatNotSupportedException(final String message) {
    super(message);
  }
}
