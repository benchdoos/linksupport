package io.github.benchdoos.linksupport.links.validators;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FileValidator {

    /**
     * Check if given file exists, and it is a file, not a directory.
     *
     * @param file to check
     * @throws IllegalArgumentException if file does not exist or a directory given instead of a file
     * @throws NullPointerException     if given file is null
     */
    public static void fileMustExistAndMustBeAFile(@NonNull final File file) throws IllegalArgumentException {
        fileMustExist(file);
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Can not get url from directory: " + file + " give file instead");
        }
    }

    /**
     * Check if given file exists.
     *
     * @param file file or directory to check, if exists
     * @throws IllegalArgumentException if file does not exist
     * @throws NullPointerException     if file is null
     */
    public static void fileMustExist(@NonNull File file) throws IllegalArgumentException {
        if (!file.exists()) {
            throw new IllegalArgumentException("Given file does not exist: " + file);
        }
    }
}
