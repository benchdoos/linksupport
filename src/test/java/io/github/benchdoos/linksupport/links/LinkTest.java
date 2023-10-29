package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.UnitTest;
import org.apache.tika.mime.MediaType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LinkTest extends UnitTest {
    @Test
    void getLinkByFile() throws URISyntaxException {
        for (Link link : Link.values()) {
            final File file = new File(
                    Objects.requireNonNull(Objects.requireNonNull(
                                    getClass().getClassLoader()
                                            .getResource("links" + File.separator + "test_link." + link.getExtension()))
                            .toURI()));
            Assertions.assertThat(file).isNotNull().exists();

            System.out.println("Checking " + link + " for file: " + file);

            final Link linkByFile = Link.getLinkForFile(file);
            Assertions.assertThat(linkByFile).isNotNull().isEqualTo(link);
            System.out.println("Checking successfully passed for: " + link);
        }
    }

    @Test
    void getLinkByFileMustThrowAnException() {
        final File unexistedFile = new File(tempDir, UUID.randomUUID() + ".extension");
        Assertions.assertThatCode(() -> Link.getLinkForFile(unexistedFile))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "{arguments}: supported media type check")
    @MethodSource("getSupportedMediaTypes")
    void getLinksByMediaTypeMustReturnTypes(final MediaType mediaType) {
        Assertions
                .assertThatCode(() -> Link.getLinksByMediaType(mediaType))
                .doesNotThrowAnyException();

        final List<Link> linkList = Link.getLinksByMediaType(mediaType);
        assertThat(linkList).isNotEmpty();
    }


    public static Stream<Arguments> getSupportedMediaTypes() {
        return MediaTypes.getSupportedMediaTypes().stream().map(Arguments::of);
    }
}
