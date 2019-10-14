**LinkSupport**

Gives functionality to get `java.net.URL` from input stream or file for such formats: `.url`, `.desktop`, `.webloc`.
Gives ability to create files for `java.net.URL`: `.url`, `.desktop`, `.webloc`.

**How to use:**

- import to your project
- use `com.github.benchdoos.linksupport.links.Link` to operate with links

**Examples:**

Get `java.net.URL` from `java.io.File`:
```
import java.io.File;
import java.io.IOException;
import java.net.URL;

class Demo {
    public static void main(String[] args) {
        final File file = new File("<path to url file>");
        try {
            final URL url = Link.INTERNET_SHORTCUT_LINK.getLinkProcessor().getUrl(file);
            System.out.println("url: " + url);
        } catch (IOException e) {
            //can not get link from file
        }
    }
}
```
from `java.io.InputStream`:
```
class Demo {
    public static void main(String[] args) {

        try {
            final InputStream is = new FileInputStream(new File("<path to file>")); //Any Input stream
            final URL url = Link.INTERNET_SHORTCUT_LINK.getLinkProcessor().getUrl(is);
            System.out.println("url: " + url);
        } catch (IOException e) {
            //can not get link from file
        }
    }
}
```

Save `java.net.URL` to `java.io.File`:
```
class Demo {
    public static void main(String[] args) {

        try {
            final URL url = new URL("https://github.com/benchdoos/LinkSupport");
            final FileOutputStream outputStream = new FileOutputStream(new File("<path to desktop file>"));
            Link.DESKTOP_LINK.getLinkProcessor().createLink(url, outputStream); //closes stream after creating file
            System.out.println("url: " + url);
        } catch (IOException e) {
            //can not create link in file
        }
    }
}
```

If you don't know exact file type, you can use `getLinkForFile(File file)` in `com.github.benchdoos.linksupport.links.Link`:
```
class Demo {
    public static void main(String[] args) {

        try {
            final File file = new File("<path to file>");
            final Link linkForFile = Link.getLinkForFile(file); //take info from file media type and try to validate it

            if (linkForFile != null) { //it can return null if it can not parse file
                final URL url = linkForFile.getLinkProcessor().getUrl(file);
                System.out.println("url: " + url);
            }
        } catch (IOException e) {
            //can not get link from file
        }
    }
}
```