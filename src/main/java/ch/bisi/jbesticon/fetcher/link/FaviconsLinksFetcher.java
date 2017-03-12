package ch.bisi.jbesticon.fetcher.link;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Component for retrieving the list of favicons links from an html {@link Document}.
 */
public class FaviconsLinksFetcher implements LinksFetcher {

  private static final Logger logger = LoggerFactory.getLogger(FaviconsLinksFetcher.class);

  /**
   * The document from which favicons link are extracted.
   **/
  private Document domainDocument;

  // @formatter:off
  private static final List<String> commonFaviconsPaths = Arrays.asList(
      // legacy way of adding favicon by simply serving /favicon.ico
      "favicon.ico",
      // no HTML way of adding an animated favicon for apple touch devices
      "apple-touch-icon.png",
      // no HTML way of adding a non animated favicon for apple touch devices
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
   * @param document the html {@link Document} from which to extract icon link, cannot be {@code
   * null}
   */
  public FaviconsLinksFetcher(final Document document) {
    this.domainDocument = document;
  }

  /**
   * Gets all the favicons link, both the common ones and the found tags for the specific domain.
   *
   * @return the {@link List} of {@link URL}
   * @throws IOException in case of problems accessing the html
   */
  @Override
  public List<URL> fetchLinks() throws IOException {
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
      final String baseHrefValue = baseHref.attr("href");
      logger.debug("<base> tag found with href: {}", baseHrefValue);
      return new URL(new URL(domainDocument.location()), baseHrefValue);
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
      logger.debug("Common favicon path {} added", faviconPath);
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
        final String hrefValue = el.attr("href");
        logger.debug("Favicon href found: {}", hrefValue);
        result.add(new URL(baseUrl, hrefValue));
      }
    }
    return result;
  }

  /**
   * Gets the {@link Document} from which favicons link are extracted.
   *
   * @return the {@link Document}
   */
  public Document getDomainDocument() {
    return this.domainDocument;
  }

}
