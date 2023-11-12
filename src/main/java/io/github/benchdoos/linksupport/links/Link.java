package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.links.impl.BinaryWeblocLinkProcessor;
import io.github.benchdoos.linksupport.links.impl.DesktopEntryLinkProcessor;
import io.github.benchdoos.linksupport.links.impl.InternetShortcutLinkProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.tika.mime.MediaType;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    SupportedMediaTypes.APPLICATION_OCTET_STREAM,
                    SupportedMediaTypes.APPLICATION_WEBLOC,
                    SupportedMediaTypes.APPLICATION_WEBLOCE
            )),

    /**
     * Windows web link
     */
    INTERNET_SHORTCUT_LINK(
            "Internet shortcut link",
            "url",
            new InternetShortcutLinkProcessor(),
            Arrays.asList(
                    SupportedMediaTypes.WWWSERVER_REDIRECTION,
                    SupportedMediaTypes.APPLICATION_INTERNET_SHORTCUT,
                    SupportedMediaTypes.APPLICATION_X_MSWINURL,
                    SupportedMediaTypes.APPLICATION_X_URL,
                    SupportedMediaTypes.MESSAGE_EXTERNAL_BODY,
                    SupportedMediaTypes.TEXT_URL,
                    SupportedMediaTypes.TEXT_X_URL)),
    /**
     * Unix desktop entry web link
     */
    DESKTOP_LINK("Desktop entry link", "desktop", new DesktopEntryLinkProcessor(),
            Collections.singletonList(SupportedMediaTypes.APPLICATION_X_DESKTOP));

    /**
     * Name of link type
     */
    private String name;

    /**
     * Extension without dot. Example: {@code file.webloc}, extension - {@code webloc}
     */
    private String extension;

    /**
     * Processor, that gives abilities
     */
    private LinkProcessor linkProcessor;

    /**
     * Supported media types for link
     */
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
     * Returns list of {@link Link} that support given Media type
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
     * Checks if current {@link Link} support given media type
     *
     * @param mediaTypeString for check
     * @return true if supports
     */
    public boolean supportsMediaType(String mediaTypeString) {
        final MediaType mediaType = MediaType.parse(mediaTypeString);
        return mediaTypes.contains(mediaType);
    }

    /**
     * Returns {@link Link} instance for given {@link java.io.File}.
     * Checks {@link org.apache.tika.mime.MediaType} of given {@link java.io.File}
     *
     * @param file target file
     * @return link if supported, otherwise - null
     */
    public static Link getLinkForFile(@NonNull File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Given file does not exist: " + file);
        }

        final Optional<Link> supported = Arrays.stream(Link.values())
                .filter(link -> link.getLinkProcessor().instance(file))
                .distinct()
                .findFirst();

        return supported.orElse(null);
    }
}
