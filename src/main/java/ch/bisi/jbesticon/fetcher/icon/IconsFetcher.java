package ch.bisi.jbesticon.fetcher.icon;

import ch.bisi.jbesticon.common.JiconIcon;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Interface implemented by components which retrieve {@link JiconIcon}s.
 */
public interface IconsFetcher {

  /**
   * Retrieves a {@link Stream} of {@link JiconIcon}s.
   *
   * @return a {@link Stream} of {@link JiconIcon}s
   * @throws IOException in case of problems retrieving the {@link JiconIcon}s
   */
  Stream<JiconIcon> getIcons() throws IOException;

}
