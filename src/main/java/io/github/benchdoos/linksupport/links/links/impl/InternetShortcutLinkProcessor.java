/*
 * (C) Copyright 2019.  Eugene Zrazhevsky and others.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * Eugene Zrazhevsky <eugene.zrazhevsky@gmail.com>
 */

package io.github.benchdoos.linksupport.links.links.impl;

import io.github.benchdoos.linksupport.links.links.LinkProcessor;
import io.github.benchdoos.linksupport.links.validators.FileValidator;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;

/**
 * Link processor for {@code .url} file
 */
public class InternetShortcutLinkProcessor implements LinkProcessor {

    private static final String INTERNET_SHORTCUT = "[InternetShortcut]";

    @Override
    public void createLink(final URL url, final OutputStream outputStream) throws IOException {
        try{
            outputStream.write((INTERNET_SHORTCUT + "\n").getBytes());
            outputStream.write((LinkUtils.URL_PREFIX + url.toString() + "\n").getBytes());
        } finally {
            outputStream.flush();
            outputStream.close();
        }

    }

    @Override
    public void createLink(@NonNull final URL url, @NonNull final File file) throws IOException {
        if (file.exists() && (!file.isFile())) {
            throw new IllegalArgumentException("Given file is a directory: " + file);
        }

        try (final OutputStream fos = Files.newOutputStream(file.toPath())) {
            createLink(url, fos);
        }
    }

    @Override
    public URL getUrl(@NonNull final InputStream inputStream) throws IOException {
        return LinkUtils.getUrl(inputStream);
    }

    @Override
    public URL getUrl(@NonNull final File file) throws IOException {
        FileValidator.fileMustExistAndMustBeAFile(file);

        try (final InputStream inputStream = Files.newInputStream(file.toPath())) {
            return getUrl(inputStream);
        }
    }

    @Override
    public boolean instance(@NonNull final File file) {
        FileValidator.fileMustExist(file);

        return LinkUtils.contains(file, INTERNET_SHORTCUT);
    }
}
