package io.github.benchdoos.linksupport.links.exceptions;

import java.io.IOException;

/**
 * Exception is thrown if given source is not a link
 */
public class InvalidSource extends IOException {
    public InvalidSource(String s) {
        super(s);
    }
}
