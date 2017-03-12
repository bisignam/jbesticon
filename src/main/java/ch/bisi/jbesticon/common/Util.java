package ch.bisi.jbesticon.common;

import java.net.MalformedURLException;
import java.net.URL;

public class Util {

  private Util() {
    // hide public constructor
  }

  /**
   * Returns the domain of a given {@link URL}. E.g for
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
   * Gets the file extension from a given path.
   *
   * @param path the path cannot be {@code null}.
   * @return the extension or {@code null} if no extension can be found.
   */
  public static String getExtension(final String path) {
    final int lastPoint = path.lastIndexOf('.');
    if (lastPoint == -1  || lastPoint == path.length() - 1) {
      return null;
    }
    return path.substring(lastPoint + 1);
  }

}