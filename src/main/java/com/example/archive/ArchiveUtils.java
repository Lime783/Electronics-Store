package com.example.archive;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class ArchiveUtils {

    public static void addToArchive(String message) {
        Path pathToArchive = Path.of("archive");
        Path archive = pathToArchive.resolve("archive.txt");
        Path archiveTmp = pathToArchive.resolve("archive.txt.tmp");

        try {
            Files.createDirectories(pathToArchive);

            String contentOfArchive = Files.readString(archive, StandardCharsets.UTF_8);
            Files.writeString(archiveTmp, contentOfArchive);
            Files.writeString(archiveTmp, message + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            Files.move(archiveTmp, archive, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
