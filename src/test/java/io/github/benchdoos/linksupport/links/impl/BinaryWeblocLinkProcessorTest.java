package io.github.benchdoos.linksupport.links.impl;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.Link;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

class BinaryWeblocLinkProcessorTest extends UnitTest {
    @Test
    void mediaTypeMustBeSupported() {
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType("application/webloc");
        assertThat(supports).isTrue();
    }

    @Test
    void mediaTypeMustNotBeSupported() throws IOException, URISyntaxException {
        final File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("links/test_link." + Link.INTERNET_SHORTCUT_LINK.getExtension())).toURI());
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isFalse();
    }

    @Test
    void createLinkMustCreateLinkViaFileOutputStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".webloc");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        try (final FileOutputStream fos = new FileOutputStream(toCreate)) {
            new BinaryWeblocLinkProcessor().createLink(urlToWrite, fos);
        }

        assertThat(toCreate).exists();
        final URL storedUrl = new BinaryWeblocLinkProcessor().getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCreateLink() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".webloc");
        assertThat(toCreate).doesNotExist();

        final URL urlToWrite = new URL("https://google.com/");
        new BinaryWeblocLinkProcessor().createLink(urlToWrite, toCreate);

        assertThat(toCreate).exists();
        final URL storedUrl = new BinaryWeblocLinkProcessor().getUrl(toCreate);

        assertThat(storedUrl).isEqualTo(urlToWrite);
    }

    @Test
    void createLinkMustCloseStream() throws IOException {
        final File toCreate = new File(tempDir, UUID.randomUUID() + ".webloc");
        assertThat(toCreate).doesNotExist();

        final OutputStream outputStream = Mockito.mock(OutputStream.class);

        final URL urlToWrite = new URL("https://google.com/");
        new BinaryWeblocLinkProcessor().createLink(urlToWrite, outputStream);

        verify(outputStream, atLeastOnce()).close();

    }

}
