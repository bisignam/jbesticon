package ch.bisi.jbesticon;

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
   * @param url
   *          the url
   * @return the {@link URL} representing the domain
   * @throws MalformedURLException
   *           in case of problems retrieving the domain
   */
  public static URL getDomain(URL url) throws MalformedURLException {
    return new URL(url.getProtocol(), url.getHost(), "");
  }

}