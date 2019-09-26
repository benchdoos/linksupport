package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.links.Link;
import com.github.benchdoos.linksupport.links.LinkProcessor;
import org.apache.logging.log4j.core.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class InternetShortcutLinkProcessorTest {
    private static final String RESOURCES = "src/test/resources";
    private static final String EXPECTED_URL = "http://test.com/";


    @org.junit.jupiter.api.Test
    void createLink() {
    }

    @org.junit.jupiter.api.Test
    void getUrlFileUrl() throws IOException {
        final File file = new File(RESOURCES + File.separator + "test_link.url");
        assertThat(file.exists()).isTrue();
        final FileInputStream fileInputStream = new FileInputStream(file);

        final Link link = Link.getByExtension(FileUtils.getFileExtension(file));
        final LinkProcessor linkProcessor = link.getLinkProcessor();
        final URL url = linkProcessor.getUrl(fileInputStream);

        assertThat(url).isNotNull();
        assertThat(url.toString()).isEqualTo(EXPECTED_URL);
    }
}