package ch.bisi.jbesticon;

/** Class representing an Icon. **/
public class Icon {
	
	private String url;
	private int width;
	private int height;
	private Format format;
	private byte[] data;

	public Icon(String url, int width, int height, Format format, byte[] data) {
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
	public void setUrl(String url) {
		this.url = url;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

}
