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

package com.github.benchdoos.linksupport.links;

import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Gives ability to create link and get url from link.
 */
public interface LinkProcessor {
    /**
     * Creates link in output stream. Closes stream after write.
     *
     * @param url to create
     * @param outputStream where to write
     * @throws IOException if can not write url to stream
     */
    void createLink(@NonNull URL url, @NonNull OutputStream outputStream) throws IOException;

    /**
     * Creates link file. Closes stream after write.
     *
     * @param url to write
     * @param file to create
     * @throws IOException if can not write url to file
     */
    void createLink(@NonNull URL url, @NonNull File file) throws IOException;

    /**
     * Gets url from input stream
     *
     * @param inputStream from where to read
     * @return url from stream
     * @throws java.io.IOException if something wrong with input-output
     * @throws java.net.MalformedURLException if url can not be parsed
     */
    URL getUrl(@NonNull InputStream inputStream) throws IOException;

    /**
     * Gets url from file
     *
     * @param file to read
     * @return url from file
     * @throws java.io.IOException if something wrong with file
     * @throws java.net.MalformedURLException if url can not bes parsed
     */
    URL getUrl(@NonNull File file) throws IOException;

    /**
     * Checks if given {@link java.io.File} is instance of current {@link com.github.benchdoos.linksupport.links.Link}
     * format. Checks {@link org.apache.tika.mime.MediaType} of given {@link java.io.File}.
     *
     * @param file file to load
     * @return true, if given {@link java.io.File} is instance of current
     * {@link com.github.benchdoos.linksupport.links.Link}
     */
    boolean instance(@NonNull File file);
}
