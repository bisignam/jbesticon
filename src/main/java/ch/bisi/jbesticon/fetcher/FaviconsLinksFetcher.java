package ch.bisi.jbesticon.fetcher;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Component for retrieving the list of favicons links.
 */
public class FaviconsLinksFetcher {

  /**
   * The document from which favicons links are extracted.
   **/
  private Document domainDocument;

  // @formatter:off
  private static final List<String> commonFaviconsPaths = Arrays.asList(
      "favicon.ico",
      "apple-touch-icon.png",
      "apple-touch-icon-precomposed.png"
  );

  private static final List<String> faviconsCssSelectors = Arrays.asList(
      "link[rel='icon']",
      "link[rel='shortcut icon']",
      "link[rel='apple-touch-icon']",
      "link[rel='apple-touch-icon-precomposed']"
  );
  // @formatter:on

  /**
   * Instantiates a new {@link FaviconsLinksFetcher}.
   *
   * @param document the html {@link Document} from which to extract icons links, cannot be {@code
   * null}
   */
  FaviconsLinksFetcher(final Document document) {
    this.domainDocument = document;
  }

  /**
   * Gets all the favicons links, both the common ones and the found tags for the specific domain.
   *
   * @return the {@link List} of {@link URL}
   * @throws IOException in case of problems accessing the html
   */
  List<URL> getLinks() throws IOException {
    final List<URL> faviconsLinks = new ArrayList<>();
    final URL baseUrl = extractBaseUrl(domainDocument);
    faviconsLinks.addAll(extractCommonFaviconsUrls(baseUrl));
    faviconsLinks.addAll(extractFaviconsUrlsFromCssSelectors(baseUrl, domainDocument));
    return faviconsLinks;
  }

  /**
   * Gets the favicons tags {@code href} attributes values.
   *
   * @return the list of specific favicons tags {@link URL}s
   * @throws MalformedURLException in case of problems extracting the {@link URL}s
   */
  List<URL> getTagsUrls() throws MalformedURLException {
    return extractFaviconsUrlsFromCssSelectors(extractBaseUrl(domainDocument), domainDocument);
  }

  /**
   * Gets the list of common favicons {@link URL}s.
   *
   * @return the list of common favicons {@link URL}s
   * @throws MalformedURLException in case of problems extracting the {@link URL}s
   */
  List<URL> getCommonUrls() throws MalformedURLException {
    return extractCommonFaviconsUrls(extractBaseUrl(domainDocument));
  }

  /**
   * Extracts the value of the href attribute of the {@code <base>} tag from the given html
   * {@link Document}.
   * See https://www.w3schools.com/tags/tag_base.asp for more information about {@code <base>}.
   *
   * @param document the html {@link Document} to process
   * @return the base {@link URL} or {@code null} if no {@code <base>} tag has been found.
   */
  private URL extractBaseUrl(final Document document) throws MalformedURLException {
    final Element baseHref = document.select("head base[href]").first();
    if (baseHref != null) {
      return new URL(new URL(domainDocument.location()), baseHref.attr("href"));
    }
    return new URL(domainDocument.location());
  }

  /**
   * Extracts the common favicons urls from the given html {@link Document}.
   *
   * @param baseUrl the base {@link URL}.
   * @return the list of common favicons {@link URL}s
   * @throws MalformedURLException in case of problems building the favicons common {@link URL}s.
   */
  private static List<URL> extractCommonFaviconsUrls(final URL baseUrl)
      throws MalformedURLException {
    final List<URL> commonFaviconsPaths = new ArrayList<>();
    for (final String faviconPath : FaviconsLinksFetcher.commonFaviconsPaths) {
      commonFaviconsPaths.add(new URL(baseUrl, faviconPath));
    }
    return commonFaviconsPaths;
  }

  /**
   * Extracts href values of commons {@code <link>} elements referring to favicons.
   *
   * @param baseUrl the base {@link URL} for the given {@link Document}
   * @param document the html {@link Document} to scan
   * @return the list of found favicons {@link URL}s
   * @throws MalformedURLException in case the urls read from the html {@link Document} are
   *         malformed
   */
  private List<URL> extractFaviconsUrlsFromCssSelectors(final URL baseUrl, final Document document)
      throws MalformedURLException {
    final List<URL> result = new ArrayList<>();
    for (final String cssSelector : faviconsCssSelectors) {
      for (final Element el : document.select(cssSelector)) {
        result.add(new URL(baseUrl, el.attr("href")));
      }
    }
    return result;
  }

  /**
   * Gets the {@link Document} from which favicons links are extracted.
   *
   * @return the {@link Document}
   */
  public Document getDomainDocument() {
    return this.domainDocument;
  }
}
