package ch.bisi.jicon.common;

import java.net.URL;
import java.util.List;

/**
 * Class representing an icon.
 **/
public class JiconIcon {

  private final URL url;
  private final List<JiconIconImage> images;

  /**
   * Instantiates a new {@link JiconIcon}.
   *
   * @param url the url of the icon
   * @param images a {@link List} of {@link JiconIconImage}s embedded in the file at the given
   * {@link URL}.
   */
  public JiconIcon(final URL url, final List<JiconIconImage> images) {
    super();
    this.url = url;
    this.images = images;
  }

  /**
   * Gets the {@link URL}.
   *
   * @return the {@link URL}
   */
  public URL getUrl() {
    return url;
  }

  /**
   * Gets the list of {@link JiconIconImage}s embedded in the image file
   * associated to this {@link JiconIcon} instance.
   *
   * @return the {@link List} of {@link JiconIconImage}s
   */
  public List<JiconIconImage> getImages() {
    return images;
  }

  @Override
  public String toString() {
    return "JiconIcon{"
        + "url=" + url
        + ", images=" + images
        + '}';
  }
}
