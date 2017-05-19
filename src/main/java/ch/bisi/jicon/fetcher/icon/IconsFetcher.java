package ch.bisi.jicon.fetcher.icon;

import ch.bisi.jicon.common.JiconIcon;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Interface implemented by components which produce a {@link Stream} of {@link JiconIcon}s.
 */
public interface IconsFetcher {

  /**
   * Produces a {@link Stream} of {@link JiconIcon}s.
   *
   * @return a {@link Stream} of {@link JiconIcon}s
   * @throws IOException in case of problems producing the {@link JiconIcon}s
   */
  Stream<JiconIcon> getIcons() throws IOException;

}
