package ch.bisi.jbesticon.fetcher.icon;

import ch.bisi.jbesticon.common.JiconIcon;

import java.io.IOException;
import java.util.List;

/**
 * Interface implemented by components which retrieve {@link JiconIcon}s.
 */
public interface IconsFetcher {
  List<JiconIcon> getIcons() throws IOException;
}
