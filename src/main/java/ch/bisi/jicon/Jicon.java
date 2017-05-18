package ch.bisi.jicon;

import static ch.bisi.jicon.common.ImageUtil.executeOperationForEachEmbeddedImage;
import static ch.bisi.jicon.common.ImageUtil.writeImagesToFiles;

import ch.bisi.jicon.colorfinder.EmptyImageException;
import ch.bisi.jicon.colorfinder.JiconColorFinder;
import ch.bisi.jicon.common.ImageFormatNotSupportedException;
import ch.bisi.jicon.common.ImageUtil;
import ch.bisi.jicon.common.JiconIcon;
import ch.bisi.jicon.common.Util;
import ch.bisi.jicon.fetcher.icon.FaviconsFetcher;
import ch.bisi.jicon.fetcher.link.FaviconsLinksFetcher;
import ch.bisi.jicon.fetcher.link.LinksFetcher;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The core public access point to the Jicon functionality.
 */
public class Jicon {

  private static final Logger logger = LoggerFactory.getLogger(Jicon.class);

  private Jicon() {
    // hide public constructor
  }

  /**
   * Retrieves all the favicons for the given {@link URL}.
   *
   * @param url a local or remote {@link URL} of an html document
   * @return all the found favicons as {@link JiconIcon}s
   * @throws IOException in case of problems retrieving the icons from the given {@link URL}
   */
  public static List<JiconIcon> retrieveAll(final URL url)
      throws IOException {
    final String domain = Util.getDomain(url);
    final LinksFetcher linksFetcher;
    if (isLocal(url)) {
      linksFetcher = new FaviconsLinksFetcher(Jsoup.parse(url.openStream(), "UTF-8", domain));
    } else {
      linksFetcher = new FaviconsLinksFetcher(Jsoup.connect(domain).get());
    }
    final FaviconsFetcher faviconsFetcher = new FaviconsFetcher(linksFetcher);
    return faviconsFetcher.getIcons().collect(Collectors.toList());
  }

  /**
   * Creates a lettericon for the given {@link URL}.
   *
   * @param url the URL of the website from which to extract the lettericon.
   * @param fallbackColor a fallback color in case no favicon can be found for the given {@link
   * URL}.
   * @param size the size of the lettericon to create.
   * @return a {@link BufferedImage} returning the lettericon.
   * @throws IOException in case of problems retrieving a base favicon from the given {@link URL}.
   * @throws EmptyImageException in case the extracted favicons are malformed.
   */
  public static BufferedImage getLetterIcon(final URL url, final Color fallbackColor,
      final Integer size) throws IOException, EmptyImageException {
    final List<JiconIcon> favicons = retrieveAll(url);
    if (favicons.isEmpty()) {
      return ImageUtil.createLetterIcon(fallbackColor, Util.getFirstLetter(url), size);
    }
    final BufferedImage faviconImage;
    try (InputStream in = favicons.get(0).getUrl().openStream()) {
      faviconImage = ImageIO.read(in);
    }
    final JiconColorFinder colorFinder = new JiconColorFinder(faviconImage);
    final Color mainFaviconColor = colorFinder.findMainColor();
    return ImageUtil.createLetterIcon(mainFaviconColor, Util.getFirstLetter(url), size);
  }

  /**
   * Checks if an {@link URL} is pointing to a local file.
   *
   * @param url the {@link URL}
   * @return true if {@link URL} of local file, false otherwise
   */
  private static boolean isLocal(final URL url) {
    return (url.getHost() == null || url.getHost().equals("") || url.getHost().equals("localhost"))
        && url.getProtocol().equals("file");
  }

  /**
   * Saves in separated files all the images embedded in each input {@link JiconIcon}.
   * The purpose of this method is to extract multiple images files for those formats which embed
   * multiple images in the same file (see for example ico). For this reason the number of
   * files saved may be greater than the number of {@link JiconIcon}s given as input.
   *
   * <p>
   * The files are saved using the original name of the icon prepended with two indexes
   * respectively indicating the processing order and the index of the image within
   * the original file.
   * </p>
   *
   * <p>
   * For example if the first {@link JiconIcon} in the input {@link List} points to an URL whose
   * file name is favicon.ico and this ico file embeds 4 images, the icon file will be saved
   * as 4 separated files with the following names:
   * </p>
   *
   * <ul>
   * <li>0_0_favicon.ico</li>
   * <li>0_1_favicon.ico</li>
   * <li>0_2_favicon.ico</li>
   * <li>0_3_favicon.ico</li>
   * </ul>
   *
   * @param icons the {@link List} of {@link JiconIcon}s to save
   * @param targetDirPath the directory where to save the images files
   * @throws IOException in case of problems saving the {@link JiconIcon}s
   * @throws ImageFormatNotSupportedException if the format of some of the input icons is not
   *         supported
   */
  public static void saveEachEmbeddedImageInDir(final List<JiconIcon> icons,
      final String targetDirPath)
      throws IOException, ImageFormatNotSupportedException {
    if (!Files.isDirectory(Paths.get(targetDirPath))) {
      throw new NotDirectoryException(targetDirPath);
    }
    for (int i = 0; i < icons.size(); i++) {
      final JiconIcon icon = icons.get(i);
      final List<BufferedImage> images = executeOperationForEachEmbeddedImage(icon.getUrl(),
          ImageReader::read);
      logger.trace("{} BufferedImage(s) retrieved for Icon {}", images.size(), icon);
      final List<String> imagesFormats = IntStream.range(0, images.size())
          .mapToObj(index -> icon.getImages().get(index).getFormat()).collect(Collectors.toList());
      final int finalI = i;
      final List<String> outputFilesPaths = IntStream.range(0, images.size())
          .mapToObj(
              index -> targetDirPath + File.separator + finalI + "_" + index + "_" + Paths.get(
                  icons.get(finalI).getUrl().toString())
                  .getFileName()).collect(Collectors.toList());
      writeImagesToFiles(images, imagesFormats, outputFilesPaths);
    }
  }

  /**
   * Saves the files associated to each {@link JiconIcon} in the given directory.
   *
   * <p>
   * The files are saved using the original name of the icon prepended with an index indicating
   * the processing order. This is needed because multiple {@link JiconIcon}s may be
   * associated with files with the same names.
   * </p>
   *
   * <p>
   * For example if the first {@link JiconIcon} in the list points to an URL whose file name
   * is favicon.ico and the second {@link JiconIcon} in the list points to an URL whose file name is
   * also favicon.ico, two files with the following names will be saved:
   * </p>
   *
   * <ul>
   * <li>0_favicon.ico</li>
   * <li>1_favicon.ico</li>
   * </ul>
   *
   * @param icons the {@link List} of {@link JiconIcon}s to save
   * @param targetDirPath the directory where to save the images files
   * @throws IOException in case of problems saving the {@link JiconIcon}s
   */
  public static void saveInDir(final List<JiconIcon> icons, final String targetDirPath)
      throws IOException {
    if (!Files.isDirectory(Paths.get(targetDirPath))) {
      throw new NotDirectoryException(targetDirPath);
    }
    for (int i = 0; i < icons.size(); i++) {
      try (InputStream in = icons.get(i).getUrl().openStream()) {
        Files.copy(in, Paths
            .get(targetDirPath + File.separator + i + "_" + Paths.get(
                icons.get(i).getUrl().toString())
                .getFileName()));
      }
    }
  }

}
