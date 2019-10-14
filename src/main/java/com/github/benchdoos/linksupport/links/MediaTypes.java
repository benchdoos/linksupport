package com.github.benchdoos.linksupport.links;

import org.apache.tika.mime.MediaType;

public class MediaTypes {
    private static final String APPLICATION = "application";
    private static final String TEXT = "text";

    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#WEBLOC_LINK}
     */
    public static MediaType APPLICATION_OCTET_STREAM = new MediaType(APPLICATION, "octet-stream");


    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType WWWSERVER_REDIRECTION = new MediaType("wwwserver", "redirection");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType APPLICATION_INTERNET_SHORTCUT = new MediaType(APPLICATION, "internet-shortcut");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType APPLICATION_X_MSWINURL = new MediaType( APPLICATION,"x-mswinurl");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType APPLICATION_X_URL = new MediaType(APPLICATION, "x-url");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType MESSAGE_EXTERNAL_BODY = new MediaType("message", "external-body");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType TEXT_URL = new MediaType(TEXT, "url");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#INTERNET_SHORTCUT_LINK}
     */
    public static MediaType TEXT_X_URL = new MediaType(TEXT, "x-url");


    /**
     * {@link org.apache.tika.mime.MediaType} for {@link com.github.benchdoos.linksupport.links.Link#DESKTOP_LINK}
     */
    public static MediaType APPLICATION_X_DESKTOP = new MediaType(APPLICATION, "x-desktop");
}
