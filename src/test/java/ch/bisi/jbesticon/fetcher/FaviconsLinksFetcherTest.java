package ch.bisi.jbesticon.fetcher;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tests for {@link FaviconsLinksFetcher}.
 */
public class FaviconsLinksFetcherTest {

  /**
   * Tests getting favicons links from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForNewYorker() throws IOException {
    String newYorkerDomain = "http://www.newyorker.com";
    Document newYorkerIndex = Jsoup.parse(this.getClass().getResourceAsStream("/newyorker.html"),
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
   * Tests getting favicons links from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForNewYorker_with_base_tag() throws IOException {
    String newYorkerDomain = "http://www.newyorker.com";
    Document newYorkerIndex = Jsoup.parse(
        this.getClass().getResourceAsStream("/newyorker_with_base_tag" + ".html"), "UTF-8",
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
   * Tests getting favicons links from a well-formed html.
   *
   * @throws IOException in case of problems reading the tests resources
   */
  @Test
  public void getTagsForDaringFireball() throws IOException {
    String daringFireballDomain = "http://daringfireball.net/";
    Document newYorkerIndex = Jsoup
        .parse(this.getClass().getResourceAsStream("/daringfireball.html"),
            "UTF-8", daringFireballDomain);
    FaviconsLinksFetcher fetcher = new FaviconsLinksFetcher(newYorkerIndex);
    List<URL> faviconsLinks = fetcher.getTagsUrls();
    assertUrlsContainPaths(faviconsLinks, daringFireballDomain, "/graphics/apple-touch-icon.png",
        "/graphics/favicon.ico?v=005");
  }

  /**
   * Asserts that a given {@link List}  of {@link URL}s contains only the given paths.
   */
  private void assertUrlsContainPaths(List<URL> urls, String domain, String... paths) {
    assertContainSameElements(urls, buildUrlsList(domain, paths),
        Comparator.comparing(URL::getPath));
  }

  /**
   * Builds a {@link List} {@link URL}s given a domain and 1..N paths.
   */
  private List<URL> buildUrlsList(final String domain, final String... paths) {
    return Arrays.stream(paths).map(p -> buildUrl(domain, p)).collect(Collectors.toList());
  }

  /**
   * Builds an {@link URL} given a domain and a path.
   *
   * @param domain the domain
   * @param path the path
   */
  private URL buildUrl(final String domain, final String path) {
    try {
      return new URL(new URL(domain), path);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Utility method for asserting that a {@link List} contains the same elements of another one.
   * Note that the method doesn't care about the ordering of elements.
   *
   * @param target the {@link List} to check
   * @param contains the {@link List} of elements that should be present in the target {@link
   * List}.
   */
  private <T> void assertContainSameElements(final List<T> target, final List<T> contains,
      final Comparator<T> comparator) {
    assertEquals(contains.size(), target.size());
    target.sort(comparator);
    contains.sort(comparator);
    IntStream.range(0, target.size())
        .forEachOrdered(i -> assertEquals(contains.get(i), target.get(i)));
  }

}