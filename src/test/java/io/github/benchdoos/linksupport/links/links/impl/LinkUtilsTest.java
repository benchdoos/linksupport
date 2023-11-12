package io.github.benchdoos.linksupport.links.links.impl;

import io.github.benchdoos.linksupport.UnitTest;
import io.github.benchdoos.linksupport.links.exceptions.InvalidSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class LinkUtilsTest extends UnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"test_link.desktop", "test_link.url"})
    void testGetUrlMustReturnExpectedUrl(final String filename) throws IOException, URISyntaxException {

        //Get only needed file
        final File file = getFileForName(filename);

        try (final InputStream inputStream = Files.newInputStream(file.toPath())) {
            final URL url = LinkUtils.getUrl(inputStream);

            assertThat(url).isNotNull().isEqualTo(new URL(EXPECTED_URL));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"test_link.webloc"})
    void testGetUrlMustThrowAnException(final String filename) throws IOException, URISyntaxException {

        //Get only needed file
        final File file = getFileForName(filename);

        try (final InputStream inputStream = Files.newInputStream(file.toPath())) {
            assertThatCode(() -> LinkUtils.getUrl(inputStream))
                    .isExactlyInstanceOf(InvalidSource.class);
        }
    }

    private static File getFileForName(String filename) throws URISyntaxException {
        return getFile()
                .map(Arguments::get)
                .flatMap(f -> Arrays.stream(f).map(o -> (File) o))
                .filter(f -> filename.equalsIgnoreCase(f.getName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can not prepare test. File does not exist with filename: " + filename));
    }
}