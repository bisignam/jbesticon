package ch.bisi.jicon;

import static ch.bisi.jicon.TestUtil.assertIsW3SchoolsIco;
import static ch.bisi.jicon.TestUtil.generateCustomHtmlDocument;
import static ch.bisi.jicon.TestUtil.getResourceUrl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ch.bisi.jicon.common.JiconIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


/**
 * Tests {@link Jicon}.
 */
public class JiconTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void retrieveAll() throws Exception {
    final List<JiconIcon> retrievedIcons = getW3SchoolsIco();
    assertEquals(1, retrievedIcons.size());
    assertIsW3SchoolsIco(retrievedIcons.get(0));
  }

  @Test
  public void saveEachEmbeddedImageInDir() throws Exception {
    final List<JiconIcon> retrievedIcons = getW3SchoolsIco();
    final File retrievedIconsFolder = temporaryFolder.newFolder();
    Jicon.saveEachEmbeddedImageInDir(retrievedIcons, retrievedIconsFolder.getAbsolutePath());
    assertTrue(new File(retrievedIconsFolder, "0_0_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_1_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_2_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_3_w3_schools.png").exists());
  }

  @Test
  public void saveInDir() throws Exception {
    final List<JiconIcon> retrievedIcons = getW3SchoolsIco();
    final File retrievedIconsFolder = temporaryFolder.newFolder();
    Jicon.saveInDir(retrievedIcons, retrievedIconsFolder.toString());
    final File retrievedIcon = new File(retrievedIconsFolder, "0_w3_schools.ico");
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    final byte[] digest = messageDigest
        .digest(Files.readAllBytes(Paths.get(retrievedIcon.toURI())));
    assertEquals("4d1579370a6dc54ccb9677e3c5924bbe",
        toHexString(digest));
  }

  private List<JiconIcon> getW3SchoolsIco() throws IOException {
    final URL htmlWithIconsUrl = generateCustomHtmlDocument(temporaryFolder.newFile(),
        getResourceUrl("/").toString(),
        getResourceUrl("/w3_schools.ico").toString(), "", "", "");
    return Jicon.retrieveAll(htmlWithIconsUrl);
  }

  /**
   * Transforms a {@code byte} array to its hexadecimal representation.
   *
   * @param digest the digest to conver
   * @return the {@link String} representing the hexadecimal value of the input digest
   */
  private String toHexString(final byte[] digest) {
    final StringBuilder sb = new StringBuilder();
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }


}