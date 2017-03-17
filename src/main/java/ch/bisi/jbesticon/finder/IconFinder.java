package ch.bisi.jbesticon.finder;

import ch.bisi.jbesticon.common.JiconIcon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class IconFinder {

  /**
   * Gets the list of favicons for the given {@link URL}.
   *
   * @param url the url from which to extract favicons.
   * @return the list of {@link JiconIcon} representing found favicons.
   * @throws MalformedURLException in case url is malformed
   */
  public static List<JiconIcon> getFavicons(final URL url) throws MalformedURLException {
    //String domainUrl = Util.getDomain(url);

    //Document doc = Jsoup.connect(domainUrl).get();
    //Element link = doc.select("a").first();
    //String relHref = link.attr("href"); // == "/"
    //String absHref = link.attr("abs:href"); // "http://jsoup.org/"

    return Collections.emptyList();
  }


}
