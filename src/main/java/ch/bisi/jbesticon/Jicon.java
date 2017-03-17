package ch.bisi.jbesticon;

import static ch.bisi.jbesticon.common.ImageUtil.executeOperationForEachEmbeddedImage;
import static ch.bisi.jbesticon.common.ImageUtil.writeImagesToFiles;

import ch.bisi.jbesticon.common.ImageFormatNotSupportedException;
import ch.bisi.jbesticon.common.JiconIcon;
import ch.bisi.jbesticon.common.Util;
import ch.bisi.jbesticon.fetcher.icon.FaviconsFetcher;
import ch.bisi.jbesticon.fetcher.link.FaviconsLinksFetcher;
import ch.bisi.jbesticon.fetcher.link.LinksFetcher;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import javax.imageio.ImageReader;

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
   * @return all the found favicons as {@link BufferedImage}s
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

  private static boolean isLocal(final URL url) {
    return url.getHost() == null && url.getProtocol().equals("file");
  }

  /**
   * Saves in a separated file all the images embedded in each input {@link JiconIcon}. The purpose
   * of this method is to extract multiple images files for those type of formats which embed
   * multiple images in the same file (see for example ico files). For this reason the number of
   * files saved may be greater than the number of {@link JiconIcon}s passed as input.
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
   *
   * <ul>
   *   <li>0_0favicon.ico</li>
   *   <li>0_1favicon.ico</li>
   *   <li>0_2favicon.ico</li>
   *   <li>0_3favicon.ico</li>
   * </ul>
   * </p>
   *
   *
   * @param icons the {@link List} of {@link JiconIcon}s to save
   * @param targetDirPath the directory where to save the files
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
                  icons.get(index).getUrl().toString())
                  .getFileName()).collect(Collectors.toList());
      writeImagesToFiles(images, imagesFormats, outputFilesPaths);
    }
  }

  /**
   * Saves the original file associated to each {@link JiconIcon} in the given directory.
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
   *
   * <ul>
   *   <li>0_favicon.ico</li>
   *   <li>1_favicon.ico</li>
   * </ul>
   * </p>
   *
   * @param icons the {@link List} of {@link JiconIcon}s to save
   * @param targetDirPath the directory where to save the files
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
