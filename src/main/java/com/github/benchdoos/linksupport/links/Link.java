package com.github.benchdoos.linksupport.links;

import com.github.benchdoos.linksupport.links.impl.BinaryWeblocLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.DesktopEntryLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.InternetShortcutLinkProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tika.mime.MediaType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_INTERNET_SHORTCUT;
import static com.github.benchdoos.linksupport.links.MediaTypes.APPLICATION_OCTET_STREAM;
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
    WEBLOC_LINK(
            "Webloc link",
            "MacOS Safari binary-based web link",
            "webloc",
            new BinaryWeblocLinkProcessor(),
            Collections.singletonList(APPLICATION_OCTET_STREAM)),

    INTERNET_SHORTCUT_LINK(
            "Internet shortcut link",
            "Windows web link",
            "url",
            new InternetShortcutLinkProcessor(),
            Arrays.asList(WWWSERVER_REDIRECTION, APPLICATION_INTERNET_SHORTCUT, APPLICATION_X_URL, MESSAGE_EXTERNAL_BODY, TEXT_URL, TEXT_X_URL)),

    DESKTOP_LINK("Desktop entry link", "Unix desktop entry link", "desktop", new DesktopEntryLinkProcessor(),
            Collections.emptyList());

    private String name;
    private String description;
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
     * @param mediaType for check
     * @return true if supports
     */
    public boolean supportsMediaType(MediaType mediaType) {
        return mediaTypes.contains(mediaType);
    }
}
