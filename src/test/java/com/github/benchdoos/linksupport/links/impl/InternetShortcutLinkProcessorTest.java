package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.AbstractTest;
import com.github.benchdoos.linksupport.links.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class InternetShortcutLinkProcessorTest  extends AbstractTest {
    @Test
    void createLink() throws IOException {
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        Link.WEBLOC_LINK.getLinkProcessor().createLink(new URL(EXPECTED_URL), arrayOutputStream);

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
        final URL url = Link.WEBLOC_LINK.getLinkProcessor().getUrl(inputStream);

        assertThat(url).isNotNull();
        assertThat(url.toString()).isEqualTo(EXPECTED_URL);
    }

    @Test
    void getUrlMustFail() {
        final String invalidUrl = "[InternetShortcut]\n" +
                "URL=invalid://url\n";
        Assertions.assertThrows(MalformedURLException.class,
                () -> Link.INTERNET_SHORTCUT_LINK.getLinkProcessor()
                        .getUrl(new ByteArrayInputStream(invalidUrl.getBytes()))
        );
    }

    @Test
    void mediaTypeMustBeSupported() throws IOException {
        final File file = new File(RESOURCES + File.separator + "test_link." + Link.INTERNET_SHORTCUT_LINK.getExtension());
        final boolean supports = Link.INTERNET_SHORTCUT_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isTrue();
    }
}