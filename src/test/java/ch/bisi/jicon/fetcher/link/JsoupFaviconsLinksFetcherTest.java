package ch.bisi.jicon.fetcher.link;

import static ch.bisi.jicon.TestUtil.assertUrlsContainPaths;
import static ch.bisi.jicon.TestUtil.getResourceStream;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.jsoup.Jsoup;
import org.junit.Test;

/**
 * Tests for {@link JsoupFaviconsLinksFetcher}.
 */
public class JsoupFaviconsLinksFetcherTest {

  /**
   * Tests getting favicons link from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForNewYorker() throws IOException {
    final String newYorkerDomain = "http://www.newyorker.com";
    final JsoupFaviconsLinksFetcher fetcher = new JsoupFaviconsLinksFetcher(
        Jsoup.parse(getResourceStream("/newyorker.html"), "UTF-8", newYorkerDomain));
    final List<URL> faviconsLinks = fetcher.fetchLinks();
    assertUrlsContainPaths(faviconsLinks, newYorkerDomain,
        "/favicon.ico",
        "/apple-touch-icon.png",
        "/apple-touch-icon-precomposed.png",
        "/wp-content/assets/dist/img/icon/apple-touch-icon-114x114-precomposed.png",
        "/wp-content/assets/dist/img/icon/apple-touch-icon-144x144-precomposed.png",
        "/wp-content/assets/dist/img/icon/apple-touch-icon-57x57-precomposed.png",
        "/wp-content/assets/dist/img/icon/apple-touch-icon-precomposed.png",
        "/wp-content/assets/dist/img/icon/apple-touch-icon.png",
        "/wp-content/assets/dist/img/icon/favicon.ico");
  }

  /**
   * Tests getting favicons link from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForNewYorker_with_base_tag() throws IOException {
    final String newYorkerDomain = "http://www.newyorker.com";
    final JsoupFaviconsLinksFetcher fetcher = new JsoupFaviconsLinksFetcher(
        Jsoup.parse(getResourceStream("/newyorker_with_base_tag.html"), "UTF-8", newYorkerDomain));
    final List<URL> faviconsLinks = fetcher.fetchLinks();
    assertUrlsContainPaths(faviconsLinks, newYorkerDomain,
        "/images/favicon.ico",
        "/images/apple-touch-icon.png",
        "/images/apple-touch-icon-precomposed.png",
        "/images/wp-content/assets/dist/img/icon/apple-touch-icon-114x114-precomposed.png",
        "/images/wp-content/assets/dist/img/icon/apple-touch-icon-144x144-precomposed.png",
        "/images/wp-content/assets/dist/img/icon/apple-touch-icon-57x57-precomposed.png",
        "/images/wp-content/assets/dist/img/icon/apple-touch-icon-precomposed.png",
        "/images/wp-content/assets/dist/img/icon/apple-touch-icon.png",
        "/images/wp-content/assets/dist/img/icon/favicon.ico");
  }

  /**
   * Tests getting favicons link from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForDaringFireball() throws IOException {
    final String daringFireballDomain = "http://daringfireball.net/";
    final JsoupFaviconsLinksFetcher fetcher = new JsoupFaviconsLinksFetcher(
        Jsoup.parse(getResourceStream("/daringfireball.html"), "UTF-8", daringFireballDomain));
    final List<URL> faviconsLinks = fetcher.fetchLinks();
    assertUrlsContainPaths(faviconsLinks, daringFireballDomain,
        "/favicon.ico",
        "/apple-touch-icon.png",
        "/apple-touch-icon-precomposed.png",
        "/graphics/apple-touch-icon.png",
        "/graphics/favicon.ico?v=005");
  }

}