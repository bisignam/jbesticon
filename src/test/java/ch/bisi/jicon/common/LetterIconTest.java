package ch.bisi.jicon.common;

import static ch.bisi.jicon.TestUtil.getResourceUrl;
import static ch.bisi.jicon.TestUtil.toHexString;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Tests {@link LetterIcon}.
 */

public class LetterIconTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  /**
   * Tests {@link LetterIcon#getImage()}.
   */
  @Test
  public void getImage() throws IOException, NoSuchAlgorithmException, URISyntaxException {
    final LetterIcon letterIcon = new LetterIcon(100, 'X', Color.CYAN);
    final BufferedImage letterIconImage = letterIcon.getImage();
    final File retrievedLetterImageIconFile = temporaryFolder.newFile();
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    ImageIO.write(letterIconImage, "png", retrievedLetterImageIconFile);
    final byte[] actualDigest = messageDigest
        .digest(Files.readAllBytes(Paths.get(retrievedLetterImageIconFile.toURI())));
    final byte[] expectedDigest = messageDigest
        .digest(Files.readAllBytes(Paths.get(getResourceUrl("/X_CYAN.png").toURI())));
    assertEquals(toHexString(expectedDigest),
        toHexString(actualDigest));
  }

}