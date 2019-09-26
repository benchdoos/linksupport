package com.github.benchdoos.linksupport.links;

import com.github.benchdoos.linksupport.links.impl.BinaryWeblocLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.DesktopEntryLinkProcessor;
import com.github.benchdoos.linksupport.links.impl.InternetShortcutLinkProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Link {
    WEBLOC_LINK("Webloc link", "MacOS Safari binary-based web link", "webloc", new BinaryWeblocLinkProcessor()),
    INTERNET_SHORTCUT_LINK("Internet shortcut link", "Windows web link", "url", new InternetShortcutLinkProcessor()),
    DESKTOP_LINK("Desktop entry link", "Unix desktop entry link", "desktop", new DesktopEntryLinkProcessor());

    private String name;
    private String description;
    private String extension;
    private LinkProcessor linkProcessor;

    /**
     * Returns link by extension
     *
     * @param extension without dot
     * @return link by given extension
     */
    public static Link getByExtension(String extension) {
        for (Link link : Link.values()) {
            if (link.extension.equalsIgnoreCase(extension)) {
                return link;
            }
        }
        return null;
    }
}
