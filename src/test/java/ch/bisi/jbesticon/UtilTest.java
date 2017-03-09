package ch.bisi.jbesticon;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * Tests {@link Util} class.
 */
public class UtilTest {
  /**
   * Tests {@link Util#getDomain(URL)}.
   *
   */
  @Test
  public void getDomain() throws MalformedURLException {
    final String testDomain = "http://adomain.com";
    final URL testUrl = new URL(new URL(testDomain), "/api/");
    assertEquals(Util.getDomain(testUrl).toString(), testDomain);
  }

}
