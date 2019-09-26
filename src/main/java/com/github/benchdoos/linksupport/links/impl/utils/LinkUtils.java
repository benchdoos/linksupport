package com.github.benchdoos.linksupport.links.impl.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class LinkUtils {

    private static final String URL_PREFIX = "URL=";

    /**
     * Gets url from line that starts with URL_PREFIX
     *
     * @param inputStream to get url from
     * @return url if line with prefix exist, otherwise null
     * @throws MalformedURLException if can not parse string into url
     * @see LinkUtils#URL_PREFIX
     */
    public static URL getUrl(InputStream inputStream) throws MalformedURLException {
        final Scanner scan = new Scanner(inputStream);
        while (scan.hasNext()) {
            final String next = scan.next();
            if (next.startsWith(URL_PREFIX)) {
                char[] buffer = new char[next.length()];
                next.getChars(4, next.length(), buffer, 0);
                final String url = new String(buffer);
                return new URL(url);
            }
        }
        return null;
    }
}
