package ch.bisi.jbesticon.fetcher.icon;

import static ch.bisi.jbesticon.TestUtil.getResourceUrl;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import ch.bisi.jbesticon.common.Icon;
import ch.bisi.jbesticon.fetcher.link.LinksFetcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class FaviconsFetcherTest {

  @Mock
  private LinksFetcher linksFetcher;

  @Test
  public void getIcons() throws Exception {
    when(linksFetcher.fetchLinks()).thenReturn(
        Arrays.asList(getResourceUrl("/w3_schools.ico"),
                      new URL("http://www.nonexistent.com/fakepath")));
    FaviconsFetcher faviconsFetcher = new FaviconsFetcher(linksFetcher);
    List<Icon> retrievedIcons = faviconsFetcher.getIcons();
    assertEquals(4, retrievedIcons.size());
  }

}