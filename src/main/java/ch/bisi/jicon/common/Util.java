package ch.bisi.jicon.common;

import java.net.MalformedURLException;
import java.net.URL;

public class Util {

  private Util() {
    // hide public constructor
  }

  /**
   * Gets the domain of a given {@link URL}. E.g for
   * http://www.youtube.com/hello returns http://www.youtube.com/.
   *
   * @param url the url
   * @return the {@link URL} representing the domain
   * @throws MalformedURLException in case of problems retrieving the domain
   */
  public static String getDomain(final URL url) throws MalformedURLException {
    return new URL(url.getProtocol(), url.getHost(), "").toString();
  }

  /**
   * Gets the first letter of the website name.
   *
   * @param url the url
   * @return the {@code char} representing the first letter of the URL host
   */
  public static char getFirstLetter(final URL url) {
    if (url.getHost().startsWith("www.") && url.getHost().length() > 4) {
      return url.getHost().charAt(4);
    }
    if (url.getHost().isEmpty()) {
      return url.getPath().charAt(0);
    }
    return url.getHost().charAt(0);
  }

  /**
   * Gets the file extension from a given path.
   *
   * @param path the path cannot be {@code null}.
   * @return the extension or {@code null} if no extension can be found.
   */
  public static String getExtension(final String path) {
    final int lastPoint = path.lastIndexOf('.');
    if (lastPoint == -1 || lastPoint == path.length() - 1) {
      return null;
    }
    return path.substring(lastPoint + 1);
  }

  /**
   * Given a file name changes the extension with the provided new extension name.
   *
   * @param filePath the path of the file
   * @param newExtension the new extension
   * @return a new {@link String} with the old extension substituted with {@code newExtension}, the
   *         original path if no extension name can be extract from the input {@code filePath}
   */
  public static String replaceExtension(final String filePath, final String newExtension) {
    final int lastPointIndex = filePath.lastIndexOf('.');
    if (lastPointIndex == -1 || lastPointIndex == filePath.length() - 1) {
      return filePath;
    }
    return filePath.substring(0, lastPointIndex + 1) + newExtension;
  }


}