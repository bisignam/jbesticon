package ch.bisi.jbesticon.fetcher.link;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Interface implemented by component which retrieve {@link URL}s.
 */
public interface LinksFetcher {
  List<URL> fetchLinks() throws IOException;
}
