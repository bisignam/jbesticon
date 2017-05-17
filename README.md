
# Jicon
[![CircleCI](https://circleci.com/gh/bisignam/jicon.svg?style=svg)](https://circleci.com/gh/bisignam/jicon)
[![Coverage Status](https://coveralls.io/repos/github/bisignam/jicon/badge.svg)](https://coveralls.io/github/bisignam/jicon)

A simple Java library for retrieving favicons from websites.

# Dependencies

## Compile time dependencies
Java 8
Maven 3 or above

## Runtime dependencies
Java 8

# Building from source
`git clone https://github.com/bisignam/jicon.git`
`cd jicon`
`mvn package`

The result of the packaging process is a jar you can find under:

`target/jicon-<project-version>.jar`

# Usage

Below a simple program to retrieve the [github](https://github.com) favicons:

```java
import ch.bisi.jicon.Jicon;
import ch.bisi.jicon.common.JiconIcon;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GetGithubFavicons {

  public static void main(final String[] args) throws IOException {
    final List<JiconIcon> githubFavicons = Jicon.retrieveAll(new URL("https://www.github.com"));
    Jicon.saveInDir(githubFavicons, "/home/osboxes/github_favicons/");
  }

}
```

The program retrieves all the favicons and stores them in `/home/osboxes/github_favicons`.
In order to avoid naming conflicts the files are saved using the original name of the icon
prepended with an index indicating the processing order. A possible outcome of the program could be:

```
home
└───osboxes
    └───github_favicons
         ├── 0_favicon.ico
         ├── 1_apple-touch-icon.png
         └── 2_favicon.ico

```

# Contributing

Java sources can be found under `src/main/java` for production code and `src/test/java` for tests.

If you want to submit a bugfix or an enhancement please follow the steps below:

 1. **Fork** the repo on GitHub
 2. **Clone** the project to your own machine
 3. **Commit** changes to your own branch
 4. **Push** your work back up to your fork
 5. Submit a **Pull request** so that I can review your changes

# License

This project is licensed under the terms of the MIT license.

See [LICENSE](https://github.com/bisignam/jicon/blob/master/LICENSE) for more information.

