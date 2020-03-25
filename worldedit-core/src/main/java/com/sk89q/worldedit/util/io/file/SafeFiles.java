package com.sk89q.worldedit.util.io.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SafeFiles {

    /**
     * A version of {@link Files#list(Path)} that won't leak resources.
     *
     * <p>
     * Instead, it immediately consumes the entire listing into a {@link List} and
     * calls {@link List#stream()}.
     * </p>
     *
     * @param dir the directory to list
     * @return an I/O-resource-free stream of the files in the directory
     * @throws IOException if an I/O error occurs
     */
    public static Stream<Path> noLeakFileList(Path dir) throws IOException {
        try (Stream<Path> stream = Files.list(dir)) {
            return stream.collect(Collectors.toList()).stream();
        }
    }

    /**
     * {@link Path#getFileName()} includes a slash sometimes for some reason.
     * This will get rid of it.
     *
     * @param path the path to get the file name for
     * @return the file name of the given path
     */
    public static String canonicalFileName(Path path) {
        return dropSlash(path.getFileName().toString());
    }

    private static String dropSlash(String name) {
        if (name.isEmpty() || name.codePointBefore(name.length()) != '/') {
            return name;
        }
        return name.substring(0, name.length() - 1);
    }

    private SafeFiles() {
    }
}
