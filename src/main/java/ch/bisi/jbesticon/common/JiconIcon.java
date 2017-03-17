package ch.bisi.jbesticon.common;

import java.awt.Image;
import java.net.URL;

/**
 * Class representing an JiconIcon.
 **/
public class JiconIcon {

  private URL url;
  private Image image;

  /**
   * Instantiates a new {@link JiconIcon}.
   *
   * @param url the url of the icon
   * @param image the {@link Image}
   */
  public JiconIcon(final URL url, final Image image) {
    super();
    this.url = url;
    this.image = image;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(final URL url) {
    this.url = url;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(final Image image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "JiconIcon{"
        + "url=" + url
        + ", image=" + image
        + '}';
  }

}
