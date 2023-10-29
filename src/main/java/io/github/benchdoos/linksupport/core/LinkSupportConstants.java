package io.github.benchdoos.linksupport.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

/**
 * Constants for library
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LinkSupportConstants {
    public static final String DEFAULT_LIBRARY_CHARSET = StandardCharsets.UTF_8.name();
}
