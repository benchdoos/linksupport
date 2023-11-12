package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.links.LinkProcessor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class LinkTypeProcessorTest extends UnitTest {

    @ParameterizedTest
    @MethodSource("getLinkProcessor")
    void createLink(final LinkProcessor linkProcessor) {
        assertThatCode(() -> linkProcessor.createLink(new URL("https://google.com"), tempDir))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("getLinkProcessor")
    void getUrl(final LinkProcessor linkProcessor) {
        assertThatCode(() -> linkProcessor.getUrl(tempDir))
                .hasMessageContaining("Can not get url from directory")
                .isExactlyInstanceOf(IllegalArgumentException.class);

        assertThatCode(() -> linkProcessor.getUrl(new File(tempDir, UUID.randomUUID() + ".extension")))
                .hasMessageContaining("Given file does not exist")
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("getLinkProcessor")
    void instance(final LinkProcessor linkProcessor) {
        final File file = new File(tempDir, UUID.randomUUID() + ".extension");
        assertThatCode(() -> linkProcessor.instance(file))
                .hasMessageContaining("Given file does not exist")
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("getLinkProcessor")
    void instanceMustReturnFalse(final LinkProcessor linkProcessor) throws IOException {
        final File file = new File(tempDir, UUID.randomUUID() + ".extension");

        try (final FileWriter writer = new FileWriter(file)) {
            writer.append("not a link");
        }

        assertThat(linkProcessor.instance(file)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getFile")
    void getUrlFromFile(final File file) throws IOException {
        final LinkType linkTypeForFile = LinkType.getLinkForFile(file);
        final URL url = linkTypeForFile.getLinkProcessor().getUrl(file);
        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(new URL(EXPECTED_URL));
    }

    @ParameterizedTest
    @MethodSource("getFile")
    void instance(final File file) {
        assertThat(file).exists();
        assertThat(file).isFile();
        assertThatCode(() -> LinkType.getLinkForFile(file)).doesNotThrowAnyException();
    }

    public static Stream<Arguments> getLinkProcessor() {
        return Arrays.stream(LinkType.values()).map(LinkType::getLinkProcessor).map(Arguments::of);
    }
}