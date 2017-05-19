package ch.bisi.jicon.examples;

import ch.bisi.jicon.Jicon;
import ch.bisi.jicon.common.JiconIcon;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * A simple example showing howto retrieve favicons from github.
 */
@SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
public class GetGithubFavicons {

  public static void main(final String[] args) throws IOException {
    final Jicon jicon = new Jicon();
    final List<JiconIcon> githubFavicons = jicon.retrieveAll(new URL("https://www.github.com"));
    jicon.saveInDir(githubFavicons, "/home/osboxes/github_favicons/");
  }

}
