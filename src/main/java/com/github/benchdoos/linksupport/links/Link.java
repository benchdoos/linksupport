package com.github.benchdoos.linksupport.links;

import com.github.benchdoos.linksupport.links.impl.BinaryWeblocLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.DesktopEntryLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.InternetShortcutLinkProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tika.mime.MediaType;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_INTERNET_SHORTCUT;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_OCTET_STREAM;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_WEBLOC;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_WEBLOCE;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_X_DESKTOP;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_X_MSWINURL;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_X_URL;
import static com.github.benchdoos.linksupport.links.MediaTypes.MESSAGE_EXTERNAL_BODY;
import static com.github.benchdoos.linksupport.links.MediaTypes.TEXT_URL;
import static com.github.benchdoos.linksupport.links.MediaTypes.TEXT_X_URL;
import static com.github.benchdoos.linksupport.links.MediaTypes.WWWSERVER_REDIRECTION;

/**
 * Link type information and processing
 */
@AllArgsConstructor
@Getter
public enum Link {
    /**
     * MacOS Safari binary-based web link
     */
    WEBLOC_LINK(
            "Webloc link",
            "webloc",
            new BinaryWeblocLinkProcessor(),
            Arrays.asList(
                    APPLICATION_OCTET_STREAM,
                    APPLICATION_WEBLOC,
                    APPLICATION_WEBLOCE
            )),

    /**
     * Windows web link
     */
    INTERNET_SHORTCUT_LINK(
            "Internet shortcut link",
            "url",
            new InternetShortcutLinkProcessor(),
            Arrays.asList(
                    WWWSERVER_REDIRECTION,
                    APPLICATION_INTERNET_SHORTCUT,
                    APPLICATION_X_MSWINURL,
                    APPLICATION_X_URL,
                    MESSAGE_EXTERNAL_BODY,
                    TEXT_URL,
                    TEXT_X_URL)),
    /**
     * Unix desktop entry web link
     */
    DESKTOP_LINK("Desktop entry link", "desktop", new DesktopEntryLinkProcessor(),
            Collections.singletonList(APPLICATION_X_DESKTOP));

    private String name;
    private String extension;
    private LinkProcessor linkProcessor;
    private List<MediaType> mediaTypes;

    /**
     * Returns link by extension
     *
     * @param extension without dot
     * @return link by given extension
     */
    public static Link getByExtension(String extension) {
        final Optional<Link> optionalLink = Arrays.stream(Link.values())
                .filter(link -> link.getExtension().equalsIgnoreCase(extension))
                .findFirst();

        return optionalLink.orElse(null);
    }

    /**
     * Returns list of {@link com.github.benchdoos.linksupport.links.Link} that support given Media type
     *
     * @param mediaType to find
     * @return links with supported mime type
     */
    public static List<Link> getLinksByMediaType(MediaType mediaType) {
        return Arrays.stream(Link.values())
                .filter(current -> current.getMediaTypes().contains(mediaType))
                .collect(Collectors.toList());
    }

    /**
     * Checks if current {@link com.github.benchdoos.linksupport.links.Link} support given media type
     *
     * @param mediaTypeString for check
     * @return true if supports
     */
    public boolean supportsMediaType(String mediaTypeString) {
        final MediaType mediaType = MediaType.parse(mediaTypeString);
        return mediaTypes.contains(mediaType);
    }

    /**
     * Returns {@link com.github.benchdoos.linksupport.links.Link} instance for given {@link java.io.File}.
     * Checks {@link org.apache.tika.mime.MediaType} of given {@link java.io.File}
     *
     * @param file target file
     * @return link if supported, otherwise - null
     */
    public static Link getLinkForFile(File file) {
        Assertions.assertThat(file).isNotNull().exists();

        final Optional<Link> supported = Arrays.stream(Link.values())
                .filter(link -> link.getLinkProcessor().instance(file))
                .distinct()
                .findFirst();

        return supported.orElse(null);
    }
}
