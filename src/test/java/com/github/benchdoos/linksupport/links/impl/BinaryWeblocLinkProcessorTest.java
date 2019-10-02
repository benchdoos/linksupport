package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.AbstractTest;
import com.github.benchdoos.linksupport.links.Link;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryWeblocLinkProcessorTest extends AbstractTest {
    @Test
    void mediaTypeMustBeSupported() throws IOException {
        final File file = new File(RESOURCES + File.separator + "test_link." + Link.WEBLOC_LINK.getExtension());
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isTrue();
    }

    @Test
    void mediaTypeMustNotBeSupported() throws IOException {
        final File file = new File(RESOURCES + File.separator + "test_link." + Link.INTERNET_SHORTCUT_LINK.getExtension());
        final boolean supports = Link.WEBLOC_LINK.supportsMediaType(Files.probeContentType(file.toPath()));
        assertThat(supports).isFalse();
    }
}
