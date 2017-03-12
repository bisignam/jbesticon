package ch.bisi.jbesticon.fetcher.icon;

/**
 * Exception indicating that a given image format is not supported.
 */
class ImageFormatNotSupportedException extends Exception {
  ImageFormatNotSupportedException(final String message) {
    super(message);
  }
}
