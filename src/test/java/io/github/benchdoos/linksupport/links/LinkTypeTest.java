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

class LinkTypeTest extends UnitTest {
    @Test
    void getLinkByFile() throws URISyntaxException {
        for (LinkType linkType : LinkType.values()) {
            final File file = new File(
                    Objects.requireNonNull(Objects.requireNonNull(
                                    getClass().getClassLoader()
                                            .getResource("links" + File.separator + "test_link." + linkType.getExtension()))
                            .toURI()));
            Assertions.assertThat(file).isNotNull().exists();

            System.out.println("Checking " + linkType + " for file: " + file);

            final LinkType linkTypeByFile = LinkType.getLinkForFile(file);
            Assertions.assertThat(linkTypeByFile).isNotNull().isEqualTo(linkType);
            System.out.println("Checking successfully passed for: " + linkType);
        }
    }

    @Test
    void getLinkByFileMustThrowAnException() {
        final File unexistedFile = new File(tempDir, UUID.randomUUID() + ".extension");
        Assertions.assertThatCode(() -> LinkType.getLinkForFile(unexistedFile))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "{arguments}: supported media type check")
    @MethodSource("getSupportedMediaTypes")
    void getLinksByMediaTypeMustReturnTypes(final MediaType mediaType) {
        Assertions
                .assertThatCode(() -> LinkType.getLinksByMediaType(mediaType))
                .doesNotThrowAnyException();

        final List<LinkType> linkTypeList = LinkType.getLinksByMediaType(mediaType);
        assertThat(linkTypeList).isNotEmpty();
    }


    public static Stream<Arguments> getSupportedMediaTypes() {
        return SupportedMediaTypes.getAllMediaTypes().stream().map(Arguments::of);
    }
}
