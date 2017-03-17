package ch.bisi.jicon.common;

import static ch.bisi.jicon.common.ImageUtil.executeOperationForEachEmbeddedImage;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Factory of {@link JiconIcon} objects.
 */
public class JiconIconFactory {

  /**
   * Builds a {@link JiconIcon} from an {@link URL}.
   *
   * @param url the {@link URL} from which to extract a {@link JiconIcon}
   * @return a instance of {@link JiconIcon}
   * @throws ImageFormatNotSupportedException in case the format of the file at the given
   *         {@link URL} is not supported
   * @throws IOException in case of problems in reading the the file
   */
  public static JiconIcon getIcon(final URL url)
      throws ImageFormatNotSupportedException, IOException {
    final List<JiconIconImage> images = executeOperationForEachEmbeddedImage(url,
        (reader, index) -> {
          return new JiconIconImage(reader.getFormatName(),
              new Dimension(reader.getWidth(index), reader.getHeight(index)));
        });
    return new JiconIcon(url, images);
  }
}
