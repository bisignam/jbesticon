package ch.bisi.jbesticon.colorfinder;

import static ch.bisi.jbesticon.TestUtil.getImage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Tests {@link JiconColorFinder}.
 */
public class JiconColorFinderTest {

  @Test
  public void testWhitePixel() throws IOException, EmptyImageException {
    assertFindsRightColor("white1x1.png", "ffffff");
  }

  @Test
  public void testBlackPixel() throws IOException, EmptyImageException {
    assertFindsRightColor("black1x1.png", "000000");
  }

  @Test
  public void testFindColors01() throws IOException, EmptyImageException {
    assertFindsRightColor("icon01.png", "0f2e64");
  }

  @Test
  public void testFindColors02() throws IOException, EmptyImageException {
    assertFindsRightColor("icon02.png", "cb1c1f");
  }

  @Test
  public void testFindColors03() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon03.png", "f48024");
  }

  @Test
  public void testFindColors04() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon04.png", "cfdc00");
  }

  @Test
  public void testFindColors05() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon05.png", "ffa700");
  }

  @Test
  public void testFindColors06() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon06.png", "ff6600");
  }

  @Test
  public void testFindColors07() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon07.png", "e61a30");
  }

  @Test
  public void testFindColors08() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon08.ico", "14e06e");
  }

  @Test
  public void testFindColors09() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon09.ico", "1c5182");
  }

  @Test
  public void testFindColors10() throws IOException, EmptyImageException {
    assertFindsRightColor( "icon10.ico", "fe6d4c");
  }

  @Test
  public void testFindColors11() throws IOException, EmptyImageException {
    assertFindsRightColor("icon11.ico", "a30000");
  }

  /**
   * Given the name of an icon residing in the classpath asserts that the {@link ColorFinder}
   * finds the input color {@link String} as main color.
   *
   * @param iconName the name of an icon residing in the classpath
   * @param expectedHexColor the expected main color expressed as an hexadecimal {@link String}
   * @throws IOException in case of problems retrieving the icon resource from the classpath
   * @throws EmptyImageException in case the color finding algorithm fails because the icon
   *         retrieved from the classpath is empty
   */
  private void assertFindsRightColor(final String iconName, final String expectedHexColor)
      throws IOException, EmptyImageException {
    final BufferedImage image = getImage("/" + iconName);
    final Color mainColor = new JiconColorFinder(image).findMainColor();
    assertEquals(fromHexStringToColor(expectedHexColor), mainColor);
  }

  /**
   * Parses a {@link String} representing an hexadecimal value into an instance of {@link Color}.
   *
   * @param hexString the hexadecimal {@link String}
   */
  private Color fromHexStringToColor(String hexString) {
    return new Color(Integer.parseInt(hexString, 16));
  }

}