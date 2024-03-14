package com.dog2657.richtext;

import java.io.IOException;

public abstract class Controller {
    public static void openFile(String path){
        try {
            char[] fileResult = FileManager.readFile(path);

            Model.getInstance().setBuffer(fileResult);
        } catch (IOException e) {
            //TODO: Add user modal message
            throw new RuntimeException(e);
        }
    }

    public static void saveFile(String path){
        FileManager.saveFile(path, Model.getInstance().getBuffer());
    }

    public static void moveCursorLeft(int moves){
        int cursor = Model.getInstance().getCursor();
        cursor += moves;
        Model.getInstance().setCursor(cursor);
    }

    public static void moveCursorRight(int moves){
        int cursor = Model.getInstance().getCursor();
        cursor -= moves;
        Model.getInstance().setCursor(cursor);
    }
}
