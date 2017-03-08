package ch.bisi.jbesticon;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class UtilTest {

	@Test
	public void getDomain() throws MalformedURLException {
		String testDomain = "http://adomain.com";
		URL testUrl = new URL(new URL(testDomain), "/api/");
		assertEquals(Util.getDomain(testUrl).toString(), testDomain);
	}
}
