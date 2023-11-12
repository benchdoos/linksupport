package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.links.links.impl.BinaryWeblocLinkProcessor;
import io.github.benchdoos.linksupport.links.links.impl.DesktopEntryLinkProcessor;
import io.github.benchdoos.linksupport.links.links.impl.InternetShortcutLinkProcessor;
import io.github.benchdoos.linksupport.links.links.LinkProcessor;
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
public enum LinkType {
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
    private final String name;

    /**
     * Extension without dot. Example: {@code file.webloc}, extension - {@code webloc}
     */
    private final String extension;

    /**
     * Processor, that gives abilities
     */
    private final LinkProcessor linkProcessor;

    /**
     * Supported media types for link
     */
    private final List<MediaType> mediaTypes;

    /**
     * Returns link by extension
     *
     * @param extension without dot
     * @return link by given extension
     */
    public static LinkType getByExtension(String extension) {
        final Optional<LinkType> optionalLink = Arrays.stream(LinkType.values())
                .filter(link -> link.getExtension().equalsIgnoreCase(extension))
                .findFirst();

        return optionalLink.orElse(null);
    }

    /**
     * Returns list of {@link LinkType} that support given Media type
     *
     * @param mediaType to find
     * @return links with supported mime type
     */
    public static List<LinkType> getLinksByMediaType(MediaType mediaType) {
        return Arrays.stream(LinkType.values())
                .filter(current -> current.getMediaTypes().contains(mediaType))
                .collect(Collectors.toList());
    }

    /**
     * Checks if current {@link LinkType} support given media type
     *
     * @param mediaTypeString for check
     * @return true if supports
     */
    public boolean supportsMediaType(String mediaTypeString) {
        final MediaType mediaType = MediaType.parse(mediaTypeString);
        return mediaTypes.contains(mediaType);
    }

    /**
     * Returns {@link LinkType} instance for given {@link java.io.File}.
     * Checks {@link org.apache.tika.mime.MediaType} of given {@link java.io.File}
     *
     * @param file target file
     * @return link if supported, otherwise - null
     */
    public static LinkType getLinkForFile(@NonNull File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Given file does not exist: " + file);
        }

        final Optional<LinkType> supported = Arrays.stream(LinkType.values())
                .filter(link -> link.getLinkProcessor().instance(file))
                .distinct()
                .findFirst();

        return supported.orElse(null);
    }
}
