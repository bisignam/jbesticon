package ch.bisi.jicon;

import static org.junit.Assert.assertEquals;

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

  private TestUtil(){
    // hide public constructor
  }

  /**
   * Asserts that a given {@link List}  of {@link URL}s contains only the given paths.
   */
  public static void assertUrlsContainPaths(List<URL> urls, String domain, String... paths) {
    assertContainSameElements(urls, buildUrlsList(domain, paths),
        Comparator.comparing(URL::getPath));
  }

  /**
   * Builds a {@link List} of {@link URL}s given a domain and 1..N paths relative to the domain.
   */
  private static List<URL> buildUrlsList(final String domain, final String... paths) {
    return Arrays.stream(paths).map(p -> buildUrl(domain, p)).collect(Collectors.toList());
  }

  /**
   * Builds an {@link URL} given a domain and a path.
   *
   * @param domain the domain
   * @param path the path
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
   * @param target the {@link List} to check
   * @param contains the {@link List} of elements that should be present in the target {@link
   * List}.
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

}
