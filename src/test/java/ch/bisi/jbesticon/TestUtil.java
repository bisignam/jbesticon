package ch.bisi.jbesticon;

import static ch.bisi.jbesticon.common.Util.getExtension;
import static org.junit.Assert.assertEquals;

import net.sf.image4j.codec.bmp.BMPDecoder;
import net.sf.image4j.codec.ico.ICODecoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

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

  /**
   * Gets a {@link BufferedImage} starting from the path of a local resource.
   *
   * @param resourcePath the path of an image file residing on the classpath
   * @return the {@link BufferedImage}
   */
  public static BufferedImage getImage(final String resourcePath) throws IOException {
    URL url = getResourceUrl(resourcePath);
    String extension = getExtension(url.toString());
    switch (extension) {
      case "ico": {
        return ICODecoder.read(url.openStream()).get(0);
      }
      case "bmp": {
        return BMPDecoder.read(url.openStream());
      }
      default: {
        return ImageIO.read(url);
      }
    }
  }

}
