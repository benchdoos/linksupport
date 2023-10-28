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

package com.github.benchdoos.linksupport.links.impl;

import com.github.benchdoos.linksupport.core.LinkSupportConstants;
import com.github.benchdoos.linksupport.links.LinkProcessor;
import lombok.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Link processor for Linux {@code .desktop} file
 */
public class DesktopEntryLinkProcessor implements LinkProcessor {
    private static final String DESKTOP_ENTRY = "[Desktop Entry]";

    @Override
    public void createLink(URL url, OutputStream outputStream) throws IOException {
        try{
            outputStream.write((DESKTOP_ENTRY + "\n").getBytes());
            outputStream.write(("Encoding=" + LinkSupportConstants.DEFAULT_LIBRARY_CHARSET + "\n").getBytes());
            outputStream.write((LinkUtils.URL_PREFIX + url.toString() + "\n").getBytes());
            outputStream.write(("Type=Link" + "\n").getBytes());
            outputStream.write(("Icon=text-html" + "\n").getBytes());
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    @Override
    public void createLink(@NonNull URL url, @NonNull File file) throws IOException {
        if (file.exists() && (!file.isFile())) {
            throw new IllegalArgumentException("Given file is a directory: " + file);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            createLink(url, fileOutputStream);
        }
    }

    @Override
    public URL getUrl(@NonNull InputStream inputStream) throws IOException {
        try{
            return LinkUtils.getUrl(inputStream);
        } finally {
            inputStream.close();
        }
    }

    @Override
    public URL getUrl(@NonNull File file) throws IOException {
        if (!file.exists()) {
            throw new IllegalArgumentException("Given file does not exist: " + file);
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            return getUrl(inputStream);
        }
    }

    @Override
    public boolean instance(@NonNull File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("Given file does not exist: " + file);
        }

        return LinkUtils.contains(file, DESKTOP_ENTRY);
    }
}
