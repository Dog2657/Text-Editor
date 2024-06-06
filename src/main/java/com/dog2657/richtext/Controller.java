package com.dog2657.richtext;

import java.io.IOException;

public abstract class Controller {
    public static void openFile(String path){
        try {
            String fileResult = FileManager.readFile(path);

            Model.getInstance().load_file(fileResult);
            Model.getInstance().setFileLocation(path);
        } catch (IOException e) {
            //TODO: Add user modal message
            throw new RuntimeException(e);
        }
    }

    public static void saveFile(String path){
        FileManager.saveFile(path, Model.getInstance().getBuffer());
    }

    public static void addCharacter(String character){
        Model.getInstance().add_text(character);
        Model.getInstance().moveCursor(1);
    }

    public static void makeNewLine(){
        Model.getInstance().newLine();
        Model.getInstance().moveCursor(1);
    }

    public static void moveCursorLeft(int moves){
        Model.getInstance().moveCursor(moves * -1);
    }

    public static void moveCursorRight(int moves){
        Model.getInstance().moveCursor(moves);
    }

    public static void delete(boolean forwards) {
        int relativeLoc = Model.getInstance().get_cursor_relative_location();
        if(Model.getInstance().get_cursor_line() <= 0 && relativeLoc <= 0)
            return;

        if(relativeLoc == 0)
            Model.getInstance().deleteLine();
        else
            Model.getInstance().delete_text(forwards);

        Model.getInstance().moveCursor(-1);
    }
}
