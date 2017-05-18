package ch.bisi.jicon.common;

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
   */
  @Test
  public void getDomain() throws MalformedURLException {
    final String testDomain = "http://adomain.com";
    final URL testUrl = new URL(new URL(testDomain), "/api/");
    assertEquals(Util.getDomain(testUrl), testDomain);
  }

  @Test
  public void getFirstLetter() throws Exception {
    final String testDomain = "http://www.adomain.com";
    final URL testUrl = new URL(new URL(testDomain), "/api/");
    assertEquals(Util.getFirstLetter(testUrl), 'a');
  }

  @Test
  public void getFirstLetterNoWww() throws Exception {
    final String testDomain = "http://adomain.com";
    final URL testUrl = new URL(new URL(testDomain), "/api/");
    assertEquals(Util.getFirstLetter(testUrl), 'a');
  }

}
