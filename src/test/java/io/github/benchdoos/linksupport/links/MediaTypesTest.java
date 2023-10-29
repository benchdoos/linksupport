package io.github.benchdoos.linksupport.links;

import io.github.benchdoos.linksupport.UnitTest;
import org.apache.tika.mime.MediaType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class MediaTypesTest extends UnitTest {

    @Test
    void getSupportedMediaTypes() {
        final List<MediaType> supportedMediaTypes = MediaTypes.getSupportedMediaTypes();
        assertThat(supportedMediaTypes).isNotNull().size().isPositive();
    }
}