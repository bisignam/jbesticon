package ch.bisi.jbesticon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class IconFinder {

  /**
   * Gets the list of favicons for the given {@link URL}.
   * 
   * @param url
   *          the url from which to extract favicons.
   * 
   * @return the list of {@link Icon} representing found favicons.
   * @throws MalformedURLException
   *           in case url is malformed
   */
  public static List<Icon> getFavicons(URL url) throws MalformedURLException {
    URL domain = Util.getDomain(url);

    return null;
  }

}
