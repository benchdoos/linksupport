package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.AbstractTest;
import com.github.benchdoos.linksupport.links.Link;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class BinaryWeblocLinkProcessorTest extends AbstractTest {
    @Test
    void mediaTypeMustBeSupported() {
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType("application/webloc");
        assertThat(supports).isTrue();
    }

    @Test
    void mediaTypeMustNotBeSupported() throws IOException, URISyntaxException {
        final File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("test_link." + Link.INTERNET_SHORTCUT_LINK.getExtension())).toURI());
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isFalse();
    }
}
