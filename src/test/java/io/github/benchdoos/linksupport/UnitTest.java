package io.github.benchdoos.linksupport;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.provider.Arguments;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class UnitTest {

    @TempDir
    public File tempDir;
    protected static final String EXPECTED_URL = "https://github.com/benchdoos/LinkSupport";

    /**
     * Get steam of arguments with {@link File} for test files.
     *
     * @return files
     * @throws URISyntaxException if something goes wrong
     */
    public static Stream<Arguments> getFile() throws URISyntaxException {
        final URL resource = UnitTest.class.getClassLoader().getResource("links/");
        final File libFolder = new File(Objects.requireNonNull(resource).toURI());
        return Arrays.stream(Objects.requireNonNull(libFolder.listFiles()))
                .filter(File::isFile)
                .filter(File::exists)
                .map(Arguments::of);
    }
}
