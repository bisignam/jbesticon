package ch.bisi.jicon.fetcher.icon;

import static ch.bisi.jicon.TestUtil.assertIsW3SchoolsIco;
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
    assertIsW3SchoolsIco(retrievedIcons.get(0));
  }

}