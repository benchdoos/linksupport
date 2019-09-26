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

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.github.benchdoos.linksupport.links.LinkProcessor;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Link processor for MacOS Safari {@code .webloc} file
 */
public class BinaryWeblocLinkProcessor implements LinkProcessor {

    @Override
    public void createLink(URL url, OutputStream outputStream) throws IOException {
        final NSDictionary root = new NSDictionary();
        root.put("URL", url.toString());
        PropertyListParser.saveAsBinary(root, outputStream);
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
        try {
            final NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(inputStream);
            return new URL(rootDict.objectForKey("URL").toString());
        } catch (PropertyListFormatException | ParseException | ParserConfigurationException | SAXException e) {
            throw new IOException("Could not parse input stream", e);
        }
    }
}
