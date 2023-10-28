package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.UnitTest;
import com.github.benchdoos.linksupport.links.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

class InternetShortcutLinkProcessorTest extends UnitTest {
    @Test
    void createLink() throws IOException {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            Link.WEBLOC_LINK.getLinkProcessor().createLink(new URL(EXPECTED_URL), arrayOutputStream);
            final URL url;
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray())) {
                url = Link.WEBLOC_LINK.getLinkProcessor().getUrl(inputStream);
            }
            assertThat(url)
                    .isNotNull()
                    .hasToString(EXPECTED_URL);

        }

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
        final boolean supports = Link.INTERNET_SHORTCUT_LINK.supportsMediaType("application/internet-shortcut");
        assertThat(supports).isTrue();
    }


    @Test
    void createLinkMustCreateLinkViaFileOutputStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".url");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        final InternetShortcutLinkProcessor internetShortcutLinkProcessor = new InternetShortcutLinkProcessor();
        try (final FileOutputStream fos = new FileOutputStream(toCreate)) {
            internetShortcutLinkProcessor.createLink(urlToWrite, fos);
        }

        assertThat(toCreate).exists();
        final URL storedUrl = internetShortcutLinkProcessor.getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCreateLink() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".url");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        final InternetShortcutLinkProcessor internetShortcutLinkProcessor = new InternetShortcutLinkProcessor();
        internetShortcutLinkProcessor.createLink(urlToWrite, toCreate);

        assertThat(toCreate).exists();
        final URL storedUrl = internetShortcutLinkProcessor.getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCloseStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".url");
        assertThat(toCreate).doesNotExist();

        final OutputStream outputStream = Mockito.mock(OutputStream.class);

        final URL urlToWrite = new URL("https://google.com/");
        new InternetShortcutLinkProcessor().createLink(urlToWrite, outputStream);

        verify(outputStream, atLeastOnce()).close();

    }
}
