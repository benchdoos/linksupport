**LinkSupport**

Gives functionality to get `java.net.URL` from input stream or file for such formats: `.url`, `.desktop`, `.webloc`.
Gives ability to create files for `java.net.URL`: `.url`, `.desktop`, `.webloc`.

**How to use:**

- import to your project
- use `io.github.benchdoos.linksupport.LinkType` to operate with links

**Examples:**

Get `java.net.URL` from `java.io.File`:
```java
import io.github.benchdoos.linksupport.links.LinkType;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class Demo {
    public static void main(final String[] args) {
        final File file = new File(args[0]); //File path
        try {
            final URL url = LinkType.INTERNET_SHORTCUT_LINK.getLinkProcessor().getUrl(file);
            System.out.println("url: " + url);
        } catch (IOException e) {
            //can not get linkType from file
        }
    }
}
```
from `java.io.InputStream`:
```java
import io.github.benchdoos.linksupport.links.LinkType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class Demo {
    public static void main(final String[] args){

        //LinkProcessor will automatically close the input stream, but better to use try with resources
        try (final InputStream fis = new FileInputStream(args[0]);) {
            final URL url = LinkType.INTERNET_SHORTCUT_LINK.getLinkProcessor().getUrl(fis);
            System.out.println("url: " + url);
        } catch (IOException e) {
            //can not get linkType from file
        }
    }
}
```

Save `java.net.URL` to `java.io.File`:
```java
import io.github.benchdoos.linksupport.links.LinkType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

class Demo {
    public static void main(final String[] args){

        //LinkProcessor will automatically close the output stream, but better to use try with resources
        try (final OutputStream fos = new FileOutputStream(args[0])) {
            final URL url = new URL("https://github.com/benchdoos/LinkSupport");

            LinkType.INTERNET_SHORTCUT_LINK.getLinkProcessor().createLink(url, fos);
            System.out.println("File: " + args[0]);
        } catch (IOException e) {
            //can not save link to file
        }
    }
}
```

If you don't know exact file type, you can use `getLinkForFile(File file)` in `io.github.benchdoos.linksupport.LinkType`:
```java
import io.github.benchdoos.linksupport.links.LinkType;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class Demo {
    public static void main(final String[] args){

        try {
            final File file = new File(args[0]);
            final LinkType linkTypeForFile = LinkType.getLinkForFile(file); //take info from file media type and try to validate it

            if (linkTypeForFile != null) { //it can return null if it can not parse file
                final URL url = linkTypeForFile.getLinkProcessor().getUrl(file);
                System.out.println("url: " + url);
            }
        } catch (IOException e) {
            //can not get linkType from file
        }
    }
}
```