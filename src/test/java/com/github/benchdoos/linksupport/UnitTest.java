package com.github.benchdoos.linksupport;

import org.junit.jupiter.api.io.TempDir;

import java.io.File;

public abstract class UnitTest {

    @TempDir
    public File tempDir;
    protected static final String RESOURCES = "src/test/resources";
    protected static final String EXPECTED_URL = "https://github.com/benchdoos/LinkSupport";
}
