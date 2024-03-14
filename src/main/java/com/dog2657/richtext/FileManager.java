package com.dog2657.richtext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileManager {

    public static char[] readFile(String pathString) throws IOException {
        Path path = Path.of(pathString);

        long size = Files.size(path);
        if(size > Integer.MAX_VALUE)
            throw new IOException("File size is bigger than buffer");

        return readFile(pathString, (int) size);
    }

    public static char[] readFile(String path, int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];

        FileReader reader = new FileReader(path);
        reader.read(buffer, 0, bufferSize);
        reader.close();


        return  buffer;
    }

    public static void saveFile(String path, char[] buffer) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(new String(buffer));
            writer.close();
        } catch (IOException e) {
            System.out.println("File write failed" + e.getMessage());
        }
    }
}