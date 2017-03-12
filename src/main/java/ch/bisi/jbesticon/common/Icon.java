package ch.bisi.jbesticon.common;

import java.awt.Image;
import java.net.URL;

/**
 * Class representing an Icon.
 **/
public class Icon {

  private URL url;
  private Image image;

  /**
   * Instantiates a new icon.
   *
   * @param url the url of the icon
   * @param image the {@link Image}
   */
  public Icon(final URL url, final Image image) {
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
    return "Icon{"
        + "url=" + url
        + ", image=" + image
        + '}';
  }

}
