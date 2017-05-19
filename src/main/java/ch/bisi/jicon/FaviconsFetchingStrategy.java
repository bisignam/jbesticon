package ch.bisi.jicon;

import ch.bisi.jicon.fetcher.icon.IconsFetcher;
import java.io.IOException;
import java.net.URL;

/**
 * The favicons fetching strategy.
 * It has the single responsibility of returning a {@link IconsFetcher}.
 */
@FunctionalInterface
public interface FaviconsFetchingStrategy {
  IconsFetcher getFaviconsFetcher(URL url) throws IOException;
}
