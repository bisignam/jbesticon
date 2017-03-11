package ch.bisi.jbesticon.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;


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
    assertEquals(Util.getDomain(testUrl), testDomain);
  }

}
