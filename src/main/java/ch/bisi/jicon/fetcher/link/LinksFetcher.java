package ch.bisi.jicon.fetcher.link;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Interface implemented by components which produce {@link URL}s.
 */
public interface LinksFetcher {

  /**
   * Produces a {@link List} of {@link URL}s.
   *
   * @return a {@link List} of {@link URL}s
   * @throws IOException in case of problems producing the {@link List} of {@link URL}s
   */
  List<URL> fetchLinks() throws IOException;
}
