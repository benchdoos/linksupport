package com.github.benchdoos.linksupport.links;

import org.apache.tika.mime.MediaType;

public class MediaTypes {
    private static final String APPLICATION = "application";
    private static final String TEXT = "text";

    //todo check all media types!
    public static MediaType APPLICATION_OCTET_STREAM = new MediaType(APPLICATION, "octet-stream");
    public static MediaType WWWSERVER_REDIRECTION = new MediaType("wwwserver", "redirection");
    public static MediaType APPLICATION_INTERNET_SHORTCUT = new MediaType(APPLICATION, "internet-shortcut");
    public static MediaType APPLICATION_X_MSWINURL = new MediaType( APPLICATION,"x-mswinurl");
    public static MediaType APPLICATION_X_URL = new MediaType(APPLICATION, "x-url");
    public static MediaType MESSAGE_EXTERNAL_BODY = new MediaType("message", "external-body");
    public static MediaType TEXT_URL = new MediaType(TEXT, "url");
    public static MediaType TEXT_X_URL = new MediaType(TEXT, "x-url");
}
