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

import com.github.benchdoos.linksupport.links.LinkProcessor;
import com.github.benchdoos.linksupport.links.impl.utils.LinkUtils;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Link processor for {@code .url} file
 */
public class InternetShortcutLinkProcessor implements LinkProcessor {

    @Override
    public void createLink(URL url, OutputStream outputStream) throws IOException {
        outputStream.write(("[InternetShortcut]\n").getBytes());
        outputStream.write(("URL=" + url.toString() + "\n").getBytes());
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void createLink(URL url, File file) throws IOException {
        assertThat(!file.isDirectory());

        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        createLink(url, fileOutputStream);
    }

    @Override
    public URL getUrl(InputStream inputStream) throws IOException {
        return LinkUtils.getUrl(inputStream);
    }

    @Override
    public URL getUrl(File file) throws IOException {
        Assertions.assertThat(file).isNotNull().exists();

        return getUrl(new FileInputStream(file));
    }
}
