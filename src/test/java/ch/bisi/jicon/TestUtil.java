package ch.bisi.jicon;

import static org.junit.Assert.assertEquals;

import ch.bisi.jicon.common.JiconIcon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tests utils.
 */
public class TestUtil {

  /**
   * Placeholder to substitute for setting the {@code base} tag when generating a custom html
   * document.
   */
  private static final String BASE_PLACEHOLDER = "${base}";

  /**
   * Placeholder to substitute for setting the href value of the {@code icon} {@code
   * <link>} when generating a custom html document.
   */
  private static final String ICON_PLACEHOLDER = "${icon}";

  /**
   * Placeholder to substitute for setting the href value of the {@code shortcut icon} {@code
   * <link>} when generating a custom html document.
   */
  private static final String SHORTCUT_ICON_PLACEHOLDER = "${shortcut_icon}";

  /**
   * Placeholder to substitute for setting the href value of the {@code apple-touch-icon} {@code
   * <link>} when generating a custom html document.
   */
  private static final String APPLE_TOUCH_PLACEHOLDER = "${apple_touch_icon}";

  /**
   * Placeholder to substitute for setting the href value of the {@code
   * apple-touch-icon-precomposed} {@code <link>} when generating a custom html document.
   */
  private static final String APPLE_TOUCH_PRECOMPOSED_PLACEHOLDER
      = "${apple_touch_icon_precomposed}";

  private TestUtil(){
    // hide public constructor
  }

  /**
   * Asserts that a given {@link List}  of {@link URL}s contains only the given paths.
   * @param urls the {@link URL}s to check
   * @param domain the base domain of the {@code paths}
   * @param paths an array of paths relative to the {@code domain}
   */
  public static void assertUrlsContainPaths(List<URL> urls, String domain, String... paths) {
    assertContainSameElements(urls, buildUrlsList(domain, paths),
        Comparator.comparing(URL::getPath));
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
   * Generates a custom HTML document for testing purposes and gets its {@link URL}.
   *
   * @param file the file where to store the generated HTML document, cannot be {@code null}
   * @param base {@code href} value of the {@code <base>} tag, cannot be {@code null}
   * @param icon {@code href} value of the {@code <link>} tag with {@code rel} value {@code icon},
   *       an empty {@link String} generates a document without the associated {@code link} tag
   * @param shortcut {@code href} value of the {@code <link>} tag with {@code rel} value {@code
   *        shortcut icon}, an empty {@link String} generates a document without
   *        the associated {@code link} tag
   * @param appleTouch {@code href} value of the {@code <link>} tag with {@code rel} value {@code
   *        apple-touch-icon}, an empty {@link String} generates a document without
   *        the associated {@code link} tag
   * @param appleTouchPrecomposed {@code href} value of the {@code <link>} tag with {@code rel}
   *        value {@code apple-touch-icon-precomposed}, an empty {@link String} generates a
   *        document without the associated {@code link} tag
   * @return the {@link URL} of the generated HTML document
   * @throws IOException in case of problems generating the html document
   */
  public static URL generateCustomHtmlDocument(final File file, final String base,
      final String icon, final String shortcut,
      final String appleTouch, final String appleTouchPrecomposed) throws IOException {
    final InputStream inputStream = getResourceStream("/base.html");
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      try (BufferedWriter stringWriter = new BufferedWriter(new FileWriter(file))) {
        while (reader.ready()) {
          final String line = reader.readLine();
          StringBuilder stringBuilder = new StringBuilder(line);
          replaceFirstOccurrenceOrEmpty(stringBuilder, BASE_PLACEHOLDER, base);
          replaceFirstOccurrenceOrEmpty(stringBuilder, ICON_PLACEHOLDER, icon);
          replaceFirstOccurrenceOrEmpty(stringBuilder, SHORTCUT_ICON_PLACEHOLDER, shortcut);
          replaceFirstOccurrenceOrEmpty(stringBuilder, APPLE_TOUCH_PLACEHOLDER, appleTouch);
          replaceFirstOccurrenceOrEmpty(stringBuilder, APPLE_TOUCH_PRECOMPOSED_PLACEHOLDER,
              appleTouchPrecomposed);
          stringWriter.write(stringBuilder.toString() + "\n");
        }
      }
    }
    return file.toURI().toURL();
  }

  /**
   * Executes an in-place replacement of the first appearance of a given substring inside
   * the given {@link StringBuilder}. If the input {@code replacement} is empty and a match is found
   * the input stringbuilder is emptied.
   *
   * @param stringBuilder the {@link StringBuilder}
   * @param toReplace the substring to replace
   * @param replacement the replacement
   *
   */
  private static void replaceFirstOccurrenceOrEmpty(final StringBuilder stringBuilder,
      final String toReplace,
      final String replacement) {
    if (stringBuilder.length() == 0) {
      return;
    }
    final Pattern pattern = Pattern.compile(Pattern.quote(toReplace));
    final Matcher matcher = pattern.matcher(stringBuilder);
    if (matcher.find()) {
      if (replacement.isEmpty()) {
        stringBuilder.setLength(0);
      } else {
        stringBuilder.replace(matcher.start(), matcher.end(), replacement);
      }
    }
  }

}
