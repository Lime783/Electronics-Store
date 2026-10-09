package com.example.archive;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class ArchiveUtils {

    private static final Object LOCK = new Object();

    public static void addToArchive(String message) {
        Path pathToArchive = Path.of("archive");
        Path archive = pathToArchive.resolve("archive.txt");
        Path archiveTmp = pathToArchive.resolve("archive.txt.tmp");

        try {
            Files.createDirectories(pathToArchive);
            synchronized (LOCK){
                String contentOfArchive = Files.readString(archive, StandardCharsets.UTF_8);
                Files.writeString(archiveTmp, contentOfArchive);
                Files.writeString(archiveTmp, message + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                Files.move(archiveTmp, archive, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetArchive(){
        Path pathToArchiveTxt = Path.of("archive", "archive.txt");
        try {
            Files.writeString(pathToArchiveTxt, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
