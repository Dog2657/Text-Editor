package com.dog2657.richtext;

import java.io.IOException;
import java.util.ArrayList;

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

    public static void moveCursorLeft(){
        Model.getInstance().moveCursor(-1);
    }

    public static void moveCursorRight(){
        Model.getInstance().moveCursor(1);
    }

    public static void moveCursorUp(){
        ArrayList<Integer> breaks = Model.getInstance().getBreaks();
        int currentLine = Model.getInstance().get_cursor_line();
        if(currentLine <= 0)
            return;

        int currentRelativeLoc = Model.getInstance().get_cursor_relative_location();
        int previousLineLength = (currentLine <= 1)? breaks.get(currentLine -1):breaks.get(currentLine -1) - breaks.get(currentLine -2);

        if(currentRelativeLoc >= previousLineLength)
            Model.getInstance().setCursor( breaks.get(currentLine - 1) );
        else
            Model.getInstance().setCursor(
                    (currentLine <= 1)? currentRelativeLoc : breaks.get(currentLine -2) - currentRelativeLoc
            );
    }

    public static void moveCursorDown(){
        ArrayList<Integer> breaks = Model.getInstance().getBreaks();
        int currentLine = Model.getInstance().get_cursor_line();
        if((breaks.size() -1) <= currentLine)
            return;

        int currentRelativeLoc = Model.getInstance().get_cursor_relative_location();

        if(currentRelativeLoc >= (breaks.get(currentLine + 1) - breaks.get(currentLine)))
            Model.getInstance().setCursor( breaks.get(currentLine + 1) );
        else
            Model.getInstance().setCursor(breaks.get(currentLine) + currentRelativeLoc + 1);
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
