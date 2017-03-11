package ch.bisi.jbesticon.common;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Class representing an Icon.
 **/
@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class Icon {

  private String url;
  private int width;
  private int height;
  private Format format;
  private byte[] data;

  /**
   * Instantiates a new icon.
   *
   * @param url the url of the icon
   * @param width the width expressed in pixels
   * @param height the height expressed in pixels
   * @param format the format
   * @param data the byte array representing image's data.
   */
  public Icon(final String url, final int width, final int height, final Format format,
      final byte[] data) {
    super();
    this.url = url;
    this.width = width;
    this.height = height;
    this.format = format;
    this.data = data;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public Format getFormat() {
    return format;
  }

  public void setFormat(final Format format) {
    this.format = format;
  }

  public void setData(final byte[] data) {
    this.data = data;
  }

  public byte[] getData() {
    return data;
  }
}
