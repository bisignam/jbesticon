package ch.bisi.jbesticon.colorfinder;

/**
 * Exception indicating that an image cannot be correctly processed because empty.
 */
public class EmptyImageException extends Exception {
  public EmptyImageException(final String message) {
    super(message);
  }
}
