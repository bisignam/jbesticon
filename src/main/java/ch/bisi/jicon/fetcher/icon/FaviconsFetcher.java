package ch.bisi.jicon.fetcher.icon;

import ch.bisi.jicon.common.ImageFormatNotSupportedException;
import ch.bisi.jicon.common.JiconIcon;
import ch.bisi.jicon.common.JiconIconFactory;
import ch.bisi.jicon.fetcher.link.LinksFetcher;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component responsible for retrieving {@link JiconIcon}s from a {@link LinksFetcher}.
 */
public class FaviconsFetcher implements IconsFetcher {

  private static final Logger logger = LoggerFactory.getLogger(FaviconsFetcher.class);

  private final LinksFetcher linksFetcher;

  /**
   * Instantiates a new {@link FaviconsFetcher}.
   *
   * @param linksFetcher a {@link LinksFetcher} instance used to retrieve the available favicons
   *        {@link URL}s
   */
  public FaviconsFetcher(final LinksFetcher linksFetcher) {
    this.linksFetcher = linksFetcher;
  }

  /**
   * Produces a {@link Stream} of {@link JiconIcon}s.
   *
   * @return a {@link Stream} of {@link JiconIcon}s
   * @throws IOException in case of problems producing the {@link JiconIcon}s
   */
  @Override
  public Stream<JiconIcon> getIcons() throws IOException {
    return linksFetcher.fetchLinks().stream().map(this::getIconFromUrl).filter(Optional::isPresent)
        .map(Optional::get);
  }

  /**
   * Gets a {@link JiconIcon} from a given {@link URL}.
   *
   * @param url the {@link URL} from which the {@link JiconIcon} data are retrieved
   * @return an {@link Optional} {@link JiconIcon}, an empty {@link Optional} if no data can be
   *         retrieved from the {@link URL}
   */
  private Optional<JiconIcon> getIconFromUrl(final URL url) {
    try {
      return Optional.of(JiconIconFactory.getIcon(url));
    } catch (IOException e) {
      logger.warn("No icons found at {}", url, e);
      return Optional.empty();
    } catch (ImageFormatNotSupportedException e) {
      logger.warn(e.getMessage(), e);
      return Optional.empty();
    }
  }


}
