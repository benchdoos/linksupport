package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.links.Link;
import com.github.benchdoos.linksupport.links.LinkProcessor;
import org.apache.logging.log4j.core.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonLinkProcessorTest  extends AbstractTest{

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
    void mediaTypeMustNotBeSupported() throws IOException {
        final File file = new File(RESOURCES + File.separator + "test_link." + Link.WEBLOC_LINK.getExtension());
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType("invalid media type");
        assertThat(supports).isFalse();
    }
}
