package ch.bisi.jicon.fetcher.icon;

import static ch.bisi.jicon.TestUtil.getResourceUrl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import ch.bisi.jicon.common.JiconIcon;
import ch.bisi.jicon.fetcher.link.LinksFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(MockitoJUnitRunner.class)
public class FaviconsFetcherTest {

  @Mock
  private LinksFetcher linksFetcher;

  @Test
  public void getMultipleIconsFromIcoFile() throws Exception {
    when(linksFetcher.fetchLinks()).thenReturn(
        Arrays.asList(getResourceUrl("/w3_schools.ico"),
                      new URL("http://www.nonexistent.com/fakepath")));
    FaviconsFetcher faviconsFetcher = new FaviconsFetcher(linksFetcher);
    List<JiconIcon> retrievedIcons = faviconsFetcher.getIcons().collect(Collectors.toList());
    assertEquals(1, retrievedIcons.size());
    assertEquals(4, retrievedIcons.get(0).getImages().size());
    assertEquals(retrievedIcons.get(0).getImages().get(0).getWidth(), 64d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(0).getHeight(), 64d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(1).getWidth(), 32d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(1).getHeight(), 32d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(2).getWidth(), 24d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(2).getHeight(), 24d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(3).getWidth(), 16d, 0.01d);
    assertEquals(retrievedIcons.get(0).getImages().get(3).getHeight(), 16d, 0.01d);
  }

}