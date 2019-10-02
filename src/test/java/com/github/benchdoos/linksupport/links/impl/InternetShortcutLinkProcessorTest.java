package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.links.Link;
import com.github.benchdoos.linksupport.links.LinkProcessor;
import org.apache.logging.log4j.core.util.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class InternetShortcutLinkProcessorTest {
    private static final String RESOURCES = "src/test/resources";
    private static final String EXPECTED_URL = "https://github.com/benchdoos/LinkSupport";


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
    void getUrlMustSuccessfullyFinish() throws IOException {
        for (Link testLink : Link.values()) {
            final File file = new File(RESOURCES + File.separator + "test_link." + testLink.getExtension());

            System.out.println(String.format("Testing getting url by link: %s from file: %s", testLink, file));

            assertThat(file.exists()).isTrue();
            final FileInputStream fileInputStream = new FileInputStream(file);

            final Link link = Link.getByExtension(FileUtils.getFileExtension(file));
            final LinkProcessor linkProcessor = link.getLinkProcessor();
            final URL url = linkProcessor.getUrl(fileInputStream);

            assertThat(url).isNotNull();
            assertThat(url.toString()).isEqualTo(EXPECTED_URL);
            assertThat(link).isEqualTo(testLink);
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
        final File file = new File(RESOURCES + File.separator + "test_link." + Link.INTERNET_SHORTCUT_LINK.getExtension());
        final boolean supports = Link.INTERNET_SHORTCUT_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isTrue();
    }
}