package io.github.benchdoos.linksupport.links;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.tika.mime.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * List of supported {@link org.apache.tika.mime.MediaType}
 */
@UtilityClass
public final class MediaTypes {
    private static final String APPLICATION = "application";
    private static final String TEXT = "text";

    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#WEBLOC_LINK}
     */
    public static final MediaType APPLICATION_OCTET_STREAM = new MediaType(APPLICATION, "octet-stream");

    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#WEBLOC_LINK}
     */
    public static final MediaType APPLICATION_WEBLOCE = new MediaType(APPLICATION, "webloce");

    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#WEBLOC_LINK}
     */
    public static final MediaType APPLICATION_WEBLOC = new MediaType(APPLICATION, "webloc");


    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType WWWSERVER_REDIRECTION = new MediaType("wwwserver", "redirection");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType APPLICATION_INTERNET_SHORTCUT = new MediaType(APPLICATION, "internet-shortcut");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType APPLICATION_X_MSWINURL = new MediaType( APPLICATION,"x-mswinurl");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType APPLICATION_X_URL = new MediaType(APPLICATION, "x-url");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType MESSAGE_EXTERNAL_BODY = new MediaType("message", "external-body");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType TEXT_URL = new MediaType(TEXT, "url");
    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#INTERNET_SHORTCUT_LINK}
     */
    public static final MediaType TEXT_X_URL = new MediaType(TEXT, "x-url");


    /**
     * {@link org.apache.tika.mime.MediaType} for {@link Link#DESKTOP_LINK}
     */
    public static final MediaType APPLICATION_X_DESKTOP = new MediaType(APPLICATION, "x-desktop");

    @Getter
    public static final List<MediaType> supportedMediaTypes = Collections.unmodifiableList(
            Stream.of(APPLICATION_OCTET_STREAM,
                            APPLICATION_WEBLOCE,
                            APPLICATION_WEBLOC,
                            WWWSERVER_REDIRECTION,
                            APPLICATION_INTERNET_SHORTCUT,
                            APPLICATION_X_MSWINURL,
                            APPLICATION_X_URL,
                            MESSAGE_EXTERNAL_BODY,
                            TEXT_URL,
                            TEXT_X_URL,
                            APPLICATION_X_DESKTOP)
                    .collect(Collectors.toList()));

}
