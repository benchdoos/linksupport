package io.github.benchdoos.linksupport.links.impl;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.LinkType;
import io.github.benchdoos.linksupport.links.links.impl.DesktopEntryLinkProcessor;
import io.github.benchdoos.linksupport.links.links.impl.InternetShortcutLinkProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

class DesktopEntryLinkProcessorTestType extends UnitTest {
    @Test
    void createLink() throws IOException {
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        LinkType.DESKTOP_LINK.getLinkProcessor().createLink(new URL(EXPECTED_URL), arrayOutputStream);

        final URL url;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray())) {
            url = LinkType.DESKTOP_LINK.getLinkProcessor().getUrl(inputStream);
        }

        assertThat(url)
                .isNotNull()
                .hasToString(EXPECTED_URL);
    }

    @Test
    void mediaTypeMustBeSupported() throws IOException {
        final boolean supports = LinkType.DESKTOP_LINK.supportsMediaType("application/x-desktop");
        assertThat(supports).isTrue();
    }


    @Test
    void createLinkMustCreateLinkViaFileOutputStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".desktop");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        final DesktopEntryLinkProcessor desktopEntryLinkProcessor = new DesktopEntryLinkProcessor();

        try (final FileOutputStream fos = new FileOutputStream(toCreate)) {
            desktopEntryLinkProcessor.createLink(urlToWrite, fos);
        }

        assertThat(toCreate).exists();
        final URL storedUrl = desktopEntryLinkProcessor.getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCreateLink() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".desktop");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        final DesktopEntryLinkProcessor desktopEntryLinkProcessor = new DesktopEntryLinkProcessor();
        desktopEntryLinkProcessor.createLink(urlToWrite, toCreate);

        assertThat(toCreate).exists();
        final URL storedUrl = desktopEntryLinkProcessor.getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCloseStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".desktop");
        assertThat(toCreate).doesNotExist();

        final OutputStream outputStream = Mockito.mock(OutputStream.class);

        final URL urlToWrite = new URL("https://google.com/");
        new InternetShortcutLinkProcessor().createLink(urlToWrite, outputStream);

        verify(outputStream, atLeastOnce()).close();

    }
}