package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.UnitTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class LinkProcessorTest extends UnitTest {

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
    @MethodSource("getFile")
    void getUrlFromFile(final File file) throws IOException {
        final Link linkForFile = Link.getLinkForFile(file);
        final URL url = linkForFile.getLinkProcessor().getUrl(file);
        assertThat(url).isNotNull();
        assertThat(url).isEqualTo(new URL(EXPECTED_URL));
    }

    @ParameterizedTest
    @MethodSource("getFile")
    void instance(final File file) {
        assertThat(file).exists();
        assertThat(file).isFile();
        assertThatCode(() -> Link.getLinkForFile(file)).doesNotThrowAnyException();
    }

    public static Stream<Arguments> getLinkProcessor() {
        return Arrays.stream(Link.values()).map(Link::getLinkProcessor).map(Arguments::of);
    }

    public static Stream<Arguments> getFile() throws URISyntaxException {
        final File libFolder = new File(Objects.requireNonNull(LinkProcessorTest.class.getClassLoader().getResource("links/")).toURI());
        return Arrays.stream(Objects.requireNonNull(libFolder.listFiles())).filter(File::isFile).filter(File::exists).map(Arguments::of);
    }
}