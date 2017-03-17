package ch.bisi.jbesticon.fetcher.icon;

import static ch.bisi.jbesticon.common.Util.getExtension;

import ch.bisi.jbesticon.common.JiconIcon;
import ch.bisi.jbesticon.fetcher.link.LinksFetcher;

import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.ico.ICODecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

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
   *        link
   */
  FaviconsFetcher(final LinksFetcher linksFetcher) {
    this.linksFetcher = linksFetcher;
  }

  /**
   * Retrieves the {@link JiconIcon}s.
   *
   * @return the {@link List} of {@link JiconIcon}s
   * @throws IOException in case of problems retrieving the {@link JiconIcon}s
   */
  @Override
  public List<JiconIcon> getIcons() throws IOException {
    return linksFetcher.fetchLinks().stream().map(this::getIcons).flatMap(List::stream)
        .collect(Collectors.toList());
  }

  /**
   * Gets a list of {@link JiconIcon}s from a given {@link URL}.
   *
   * @param url the {@link URL} from which the {@link JiconIcon}s data are retrieved
   * @return a {@link List} of {@link JiconIcon}s, an empty {@link List} if no {@link JiconIcon}s could be
   *         retrieved from the {@link URL}
   */
  private List<JiconIcon> getIcons(final URL url) {
    try {
      final List<? extends Image> images = getImages(url);
      return images.stream().map(i -> new JiconIcon(url, i)).collect(Collectors.toList());
    } catch (IOException e) {
      logger.warn("No icons found at {}", url, e);
      return Collections.emptyList();
    } catch (ImageFormatNotSupportedException e) {
      logger.warn(e.getMessage(), e);
      return Collections.emptyList();
    }
  }

  /**
   * Gets a list of {@link Image}s from  a given {@link URL}.
   *
   * @param url the {@link URL}
   * @return a {@link List} of {@link Image}s, an empty {@link List} if no images have been found
   * @throws IOException in case of problems retrieving the image from the {@link URL}
   */
  private List<? extends Image> getImages(final URL url)
      throws IOException, ImageFormatNotSupportedException {
    String extension = getExtension(url.toString());
    if (extension == null) {
      extension = "UNKNOWN";
    }
    switch (extension) {
      case "ico": {
        return ICODecoder.read(url.openStream());
      }
      case "bmp": {
        return Collections.singletonList(BMPDecoder.read(url.openStream()));
      }
      default: {
        return Collections.singletonList(getImage(url, extension));
      }
    }
  }

  /**
   * Gets a {@link Image} from a {@link URL}.
   *
   * @param url the {@link URL}
   * @param format the format of the image
   * @return the {@link Image}
   * @throws IOException in case of problems decoding the {@link Image}
   */
  private Image getImage(final URL url, final String format)
      throws IOException, ImageFormatNotSupportedException {
    final Image image = ImageIO.read(url);
    if (image == null) {
      throw new ImageFormatNotSupportedException("Image format " + format + " not supported");
    }
    return image;
  }

}
