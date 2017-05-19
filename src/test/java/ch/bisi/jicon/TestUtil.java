package ch.bisi.jicon;

import static org.junit.Assert.assertEquals;

import ch.bisi.jicon.common.JiconIcon;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tests utils.
 */
public class TestUtil {

  private TestUtil() {
    // hide public constructor
  }

  /**
   * Asserts that a given {@link List} of {@link URL}s contains only the given paths.
   *
   * @param urls the {@link URL}s to check
   * @param domain the base domain of the {@code paths}
   * @param paths an array of paths relative to the {@code domain}
   */
  public static void assertUrlsContainPaths(List<URL> urls, String domain, String... paths) {
    assertContainSameElements(urls, buildUrlsList(domain, paths),
        Comparator.comparing(URL::toString));
  }

  /**
   * Builds a {@link List} of {@link URL}s given a domain and 1..N paths relative to the domain.
   *
   * @param domain the base domain for the given {@code paths}
   * @param paths an array of paths to relative to the base {@code domain}
   * @return a {@link List} of {@link URL}s
   */
  private static List<URL> buildUrlsList(final String domain, final String... paths) {
    return Arrays.stream(paths).map(p -> buildUrl(domain, p)).collect(Collectors.toList());
  }

  /**
   * Builds an {@link URL} given a domain and a path.
   *
   * @param domain the domain
   * @param path the path
   * @return the {@link URL}
   */
  private static URL buildUrl(final String domain, final String path) {
    try {
      return new URL(new URL(domain), path);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Utility method for asserting that a {@link List} contains the same elements of another one.
   * Note that the method doesn't assert that the two {@link List}s have the same ordering.
   *
   * @param <T> the type of the elements in input {@link List}s
   * @param target the {@link List} to check
   * @param contains the {@link List} of elements that should be present in the target {@link
   * List}.
   * @param comparator the comparator to be used
   */
  private static <T> void assertContainSameElements(final List<T> target, final List<T> contains,
      final Comparator<T> comparator) {
    assertEquals(contains.size(), target.size());
    target.sort(comparator);
    contains.sort(comparator);
    IntStream.range(0, target.size())
        .forEachOrdered(i -> assertEquals(contains.get(i), target.get(i)));
  }

  /**
   * Asserts that the input {@link JiconIcon} represents the w3_schools ico fle.
   *
   * @param jiconIcon the {@link JiconIcon} to check
   */
  public static void assertIsW3SchoolsIco(JiconIcon jiconIcon) {
    assertEquals(4, jiconIcon.getImages().size());
    assertEquals(jiconIcon.getImages().get(0).getWidth(), 64d, 0.01d);
    assertEquals(jiconIcon.getImages().get(0).getHeight(), 64d, 0.01d);
    assertEquals(jiconIcon.getImages().get(1).getWidth(), 32d, 0.01d);
    assertEquals(jiconIcon.getImages().get(1).getHeight(), 32d, 0.01d);
    assertEquals(jiconIcon.getImages().get(2).getWidth(), 24d, 0.01d);
    assertEquals(jiconIcon.getImages().get(2).getHeight(), 24d, 0.01d);
    assertEquals(jiconIcon.getImages().get(3).getWidth(), 16d, 0.01d);
    assertEquals(jiconIcon.getImages().get(3).getHeight(), 16d, 0.01d);
  }

  /**
   * Gets the {@link InputStream} of a local resource.
   *
   * @param resourcePath the path of the local resource.
   * @return the {@link InputStream} for reading the local resource
   */
  public static InputStream getResourceStream(final String resourcePath) {
    return ClassLoader.class.getResourceAsStream(resourcePath);
  }

  /**
   * Gets the {@link URL} of a local resource.
   *
   * @param resourcePath the path of the local resource.
   * @return the {@link URL} of the local resource.
   */
  public static URL getResourceUrl(final String resourcePath) {
    return ClassLoader.class.getResource(resourcePath);
  }


  /**
   * Converts a {@code byte}s array to its hexadecimal representation.
   *
   * @param digest the digest to converts
   * @return the {@link String} representing the hexadecimal value of the input digest
   */
  public static String toHexString(final byte[] digest) {
    final StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }

}
