package io.github.benchdoos.linksupport.links.impl;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.LinkType;
import io.github.benchdoos.linksupport.links.links.LinkProcessor;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class CommonLinkProcessorTestType extends UnitTest {

    @Test
    void getUrlMustSuccessfullyFinish() throws IOException, URISyntaxException {
        for (LinkType testLinkType : LinkType.values()) {
            final File file = new File(
                    Objects.requireNonNull(
                            Objects.requireNonNull(
                                            getClass().getClassLoader()
                                                    .getResource("links" + File.separator + "test_link." + testLinkType.getExtension()))
                                    .toURI()));

            System.out.printf("Testing getting url by link: %s from file: %s%n", testLinkType, file);

            assertThat(file).exists();
            final FileInputStream fileInputStream = new FileInputStream(file);

            final LinkType linkType = LinkType.getByExtension(FilenameUtils.getExtension(file.getName()));
            final LinkProcessor linkProcessor = linkType.getLinkProcessor();
            final URL url = linkProcessor.getUrl(fileInputStream);

            assertThat(url).isNotNull();
            assertThat(url).hasToString(EXPECTED_URL);
            assertThat(linkType).isEqualTo(testLinkType);
        }
    }


    @Test
    void getUrlFromFileMustSuccessfullyFinish() throws IOException, URISyntaxException {
        for (LinkType testLinkType : LinkType.values()) {
            final File file = new File(
                    Objects.requireNonNull(getClass().getClassLoader()
                                    .getResource("links" + File.separator + "test_link." + testLinkType.getExtension()))
                            .toURI());

            System.out.printf("Testing getting url by link: %s from file: %s%n", testLinkType, file);

            assertThat(file).exists();
            final LinkType linkType = LinkType.getByExtension(FilenameUtils.getExtension(file.getName()));
            final LinkProcessor linkProcessor = linkType.getLinkProcessor();
            final URL url = linkProcessor.getUrl(file);

            assertThat(url).isNotNull();
            assertThat(url).hasToString(EXPECTED_URL);
            assertThat(linkType).isEqualTo(testLinkType);
        }
    }

    @Test
    void mediaTypeMustNotBeSupported() {
        final boolean supports = LinkType.WEBLOC_LINK.supportsMediaType("invalid media type");
        assertThat(supports).isFalse();
    }
}
