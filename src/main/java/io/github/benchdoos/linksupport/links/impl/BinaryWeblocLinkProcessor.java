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

package io.github.benchdoos.linksupport.links.impl;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import io.github.benchdoos.linksupport.links.LinkProcessor;
import lombok.NonNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;

/**
 * Link processor for MacOS Safari {@code .webloc} file
 */
public class BinaryWeblocLinkProcessor implements LinkProcessor {

    @Override
    public void createLink(@NonNull URL url, @NonNull OutputStream outputStream) throws IOException {
        try{
            final NSDictionary root = new NSDictionary();
            root.put("URL", url.toString());
            PropertyListParser.saveAsBinary(root, outputStream);
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
        try {
            final NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(inputStream);
            return new URL(rootDict.objectForKey("URL").toString());
        } catch (PropertyListFormatException | ParseException | ParserConfigurationException | SAXException e) {
            throw new IOException("Could not parse input stream", e);
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

        try (final FileInputStream fileInputStream = new FileInputStream(file)) {
            final NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(fileInputStream);
            return rootDict.containsKey("URL");
        } catch (final Exception e) {
            return false;
        }
    }
}
