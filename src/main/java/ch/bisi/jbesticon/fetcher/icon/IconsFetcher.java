package ch.bisi.jbesticon.fetcher.icon;

import ch.bisi.jbesticon.common.Icon;

import java.io.IOException;
import java.util.List;

/**
 * Interface implemented by components which retrieve {@link Icon}s.
 */
public interface IconsFetcher {
  List<Icon> getIcons() throws IOException;
}
