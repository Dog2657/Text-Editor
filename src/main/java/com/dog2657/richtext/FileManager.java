package com.dog2657.richtext;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileManager {
    public static char[] readFile(String path){
        int bufferSize = 1024;
        char[] buffer = new char[bufferSize];
        try {
            FileReader reader = new FileReader(path);
            reader.read(buffer, 0, bufferSize);
            reader.close();
        } catch (IOException e) {
            System.out.println("File read failed" + e.getMessage());
        }

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