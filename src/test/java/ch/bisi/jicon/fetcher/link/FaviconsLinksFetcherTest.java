package ch.bisi.jicon.fetcher.link;

import static ch.bisi.jicon.TestUtil.assertUrlsContainPaths;
import static ch.bisi.jicon.TestUtil.getResourceStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Tests for {@link FaviconsLinksFetcher}.
 */
public class FaviconsLinksFetcherTest {

  /**
   * Tests getting favicons link from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForNewYorker() throws IOException {
    String newYorkerDomain = "http://www.newyorker.com";
    Document newYorkerIndex = Jsoup.parse(getResourceStream("/newyorker.html"),
        "UTF-8", newYorkerDomain);
    FaviconsLinksFetcher fetcher = new FaviconsLinksFetcher(newYorkerIndex);
    List<URL> faviconsLinks = fetcher.getTagsUrls();
    assertUrlsContainPaths(faviconsLinks, newYorkerDomain,
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
    String newYorkerDomain = "http://www.newyorker.com";
    Document newYorkerIndex = Jsoup.parse(
        getResourceStream("/newyorker_with_base_tag" + ".html"), "UTF-8",
        newYorkerDomain);
    FaviconsLinksFetcher fetcher = new FaviconsLinksFetcher(newYorkerIndex);
    List<URL> faviconsLinks = fetcher.getTagsUrls();
    assertUrlsContainPaths(faviconsLinks, newYorkerDomain,
        "images/wp-content/assets/dist/img/icon/apple-touch-icon-114x114-precomposed.png",
        "images/wp-content/assets/dist/img/icon/apple-touch-icon-144x144-precomposed.png",
        "images/wp-content/assets/dist/img/icon/apple-touch-icon-57x57-precomposed.png",
        "images/wp-content/assets/dist/img/icon/apple-touch-icon-precomposed.png",
        "images/wp-content/assets/dist/img/icon/apple-touch-icon.png",
        "images/wp-content/assets/dist/img/icon/favicon.ico");
  }

  /**
   * Tests getting favicons link from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForDaringFireball() throws IOException {
    String daringFireballDomain = "http://daringfireball.net/";
    Document newYorkerIndex = Jsoup
        .parse(getResourceStream("/daringfireball.html"),
            "UTF-8", daringFireballDomain);
    FaviconsLinksFetcher fetcher = new FaviconsLinksFetcher(newYorkerIndex);
    List<URL> faviconsLinks = fetcher.getTagsUrls();
    assertUrlsContainPaths(faviconsLinks, daringFireballDomain, "/graphics/apple-touch-icon.png",
        "/graphics/favicon.ico?v=005");
  }

}