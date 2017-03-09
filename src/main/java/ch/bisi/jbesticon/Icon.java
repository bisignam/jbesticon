package ch.bisi.jbesticon;

/** Class representing an Icon. **/
public class Icon {

  private String url;
  private int width;
  private int height;
  private Format format;
  private byte[] data;

  /**
   * Instantiates a new icon.
   *
   * @param url
   *          the url
   * @param width
   *          the width expressed in pixels
   * @param height
   *          the height expressed in pixels
   * @param format
   *          the format
   * @param data
   *          the byte array representing image's data.
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

  /**
   * Gets the url.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the url.
   *
   * @param url
   *          the new url
   */
  public void setUrl(final String url) {
    this.url = url;
  }

  /**
   * Gets the width.
   *
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the width.
   *
   * @param width
   *          the new width
   */
  public void setWidth(final int width) {
    this.width = width;
  }

  /**
   * Gets the height.
   *
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height.
   *
   * @param height
   *          the new height
   */
  public void setHeight(final int height) {
    this.height = height;
  }

  /**
   * Gets the format.
   *
   * @return the format
   */
  public Format getFormat() {
    return format;
  }

  /**
   * Sets the format.
   *
   * @param format
   *          the new format
   */
  public void setFormat(final Format format) {
    this.format = format;
  }

  /**
   * Gets the data.
   *
   * @return the data
   */
  public byte[] getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param data
   *          the new data
   */
  public void setData(final byte[] data) {
    this.data = data;
  }

}
