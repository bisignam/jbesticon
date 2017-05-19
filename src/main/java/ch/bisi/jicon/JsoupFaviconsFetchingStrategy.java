package ch.bisi.jicon;

import ch.bisi.jicon.common.Util;
import ch.bisi.jicon.fetcher.icon.FaviconsFetcher;
import ch.bisi.jicon.fetcher.link.JsoupFaviconsLinksFetcher;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Favicons fetching strategy which establish and HTTP connection to the {@link URL} and uses
 * {@link Jsoup} for parsing the {@link Document}.
 */
public class JsoupFaviconsFetchingStrategy implements FaviconsFetchingStrategy {

  @Override
  public FaviconsFetcher getFaviconsFetcher(final URL url) throws IOException {
    final Document document = Jsoup.connect(Util.getDomain(url)).get();
    return new FaviconsFetcher(new JsoupFaviconsLinksFetcher(document));
  }

}
