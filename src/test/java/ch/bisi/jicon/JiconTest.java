package ch.bisi.jicon;

import static ch.bisi.jicon.TestUtil.assertIsW3SchoolsIco;
import static ch.bisi.jicon.TestUtil.getResourceUrl;
import static ch.bisi.jicon.TestUtil.toHexString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ch.bisi.jicon.common.ImageFormatNotSupportedException;
import ch.bisi.jicon.common.JiconIcon;
import ch.bisi.jicon.common.JiconIconFactory;
import ch.bisi.jicon.common.LetterIcon;
import ch.bisi.jicon.fetcher.icon.IconsFetcher;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


/**
 * Tests {@link Jicon}.
 */
public class JiconTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  private URL w3SchoolsUrl;

  /** The public API under test. **/
  public Jicon jicon;

  /** Initialization executed before each test. **/
  @Before
  public void setUp() throws IOException, ImageFormatNotSupportedException {
    w3SchoolsUrl = new URL("https://www.w3schools.com/");
    final JiconIcon w3SchoolsIcon = JiconIconFactory.getIcon(getResourceUrl("/w3_schools.ico"));
    final IconsFetcher iconsFetcher = () -> Stream.of(w3SchoolsIcon);
    jicon = new Jicon((url) -> iconsFetcher);
  }

  @Test
  public void retrieveAll() throws Exception {
    final List<JiconIcon> retrievedIcons = jicon
        .retrieveAll(w3SchoolsUrl);
    assertEquals(1, retrievedIcons.size());
    assertIsW3SchoolsIco(retrievedIcons.get(0));
  }

  @Test
  public void getLetterIcon() throws Exception {
    final LetterIcon retrievedLetterIcon = jicon
        .getLetterIcon(w3SchoolsUrl, Color.RED, 100);
    assertEquals(new Integer(100), retrievedLetterIcon.getSize());
    assertEquals('W', retrievedLetterIcon.getLetter());
    assertEquals(new Color(135,197,64), retrievedLetterIcon.getBackgroundColor());
  }

  @Test
  public void saveEachEmbeddedImageInDir() throws Exception {
    final List<JiconIcon> retrievedIcons = jicon
        .retrieveAll(w3SchoolsUrl);
    final File retrievedIconsFolder = temporaryFolder.newFolder();
    jicon.saveEachEmbeddedImageInDir(retrievedIcons, retrievedIconsFolder.getAbsolutePath());
    assertTrue(new File(retrievedIconsFolder, "0_0_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_1_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_2_w3_schools.png").exists());
    assertTrue(new File(retrievedIconsFolder, "0_3_w3_schools.png").exists());
  }

  @Test
  public void saveInDir() throws Exception {
    final List<JiconIcon> retrievedIcons = jicon
        .retrieveAll(w3SchoolsUrl);
    final File retrievedIconsFolder = temporaryFolder.newFolder();
    jicon.saveInDir(retrievedIcons, retrievedIconsFolder.toString());
    final File retrievedIcon = new File(retrievedIconsFolder, "0_w3_schools.ico");
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    final byte[] digest = messageDigest
        .digest(Files.readAllBytes(Paths.get(retrievedIcon.toURI())));
    assertEquals("4d1579370a6dc54ccb9677e3c5924bbe",
        toHexString(digest));
  }


}