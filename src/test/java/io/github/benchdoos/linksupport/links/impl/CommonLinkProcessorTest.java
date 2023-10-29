package io.github.benchdoos.linksupport.links.impl;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.Link;
import io.github.benchdoos.linksupport.links.LinkProcessor;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CommonLinkProcessorTest extends UnitTest {

    @Test
    void getUrlMustSuccessfullyFinish() throws IOException, URISyntaxException {
        for (Link testLink : Link.values()) {
            final File file = new File(
                    Objects.requireNonNull(
                            Objects.requireNonNull(
                                            getClass().getClassLoader()
                                                    .getResource("links" + File.separator + "test_link." + testLink.getExtension()))
                                    .toURI()));

            System.out.printf("Testing getting url by link: %s from file: %s%n", testLink, file);

            assertThat(file).exists();
            final FileInputStream fileInputStream = new FileInputStream(file);

            final Link link = Link.getByExtension(FilenameUtils.getExtension(file.getName()));
            final LinkProcessor linkProcessor = link.getLinkProcessor();
            final URL url = linkProcessor.getUrl(fileInputStream);

            assertThat(url).isNotNull();
            assertThat(url).hasToString(EXPECTED_URL);
            assertThat(link).isEqualTo(testLink);
        }
    }


    @Test
    void getUrlFromFileMustSuccessfullyFinish() throws IOException, URISyntaxException {
        for (Link testLink : Link.values()) {
            final File file = new File(
                    Objects.requireNonNull(getClass().getClassLoader()
                                    .getResource("links" + File.separator + "test_link." + testLink.getExtension()))
                            .toURI());

            System.out.printf("Testing getting url by link: %s from file: %s%n", testLink, file);

            assertThat(file).exists();
            final Link link = Link.getByExtension(FilenameUtils.getExtension(file.getName()));
            final LinkProcessor linkProcessor = link.getLinkProcessor();
            final URL url = linkProcessor.getUrl(file);

            assertThat(url).isNotNull();
            assertThat(url).hasToString(EXPECTED_URL);
            assertThat(link).isEqualTo(testLink);
        }
    }

    @Test
    void mediaTypeMustNotBeSupported() {
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType("invalid media type");
        assertThat(supports).isFalse();
    }
}
