package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.Selection;
import javafx.scene.input.MouseEvent;

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

    public static void moveCursorLeft(boolean shiftDown){
        if(shiftDown){
            if(Model.getInstance().getCursor().getSelection() == null) {
                Selection selection = new Selection(Model.getInstance().getCursorPosition());
                selection.setEnd(Model.getInstance().getCursorPosition() - 1);
                Model.getInstance().getCursor().setSelection(selection);
            }else{
                Selection selection = Model.getInstance().getCursor().getSelection();
                selection.setEnd(selection.getEnd() - 1);
            }
        }

        Model.getInstance().moveCursor(-1);
    }

    public static void moveCursorRight(boolean shiftDown){
        if(shiftDown){
            if(Model.getInstance().getCursor().getSelection() == null) {
                Selection selection = new Selection(Model.getInstance().getCursorPosition());
                selection.setEnd(Model.getInstance().getCursorPosition() + 1);
                Model.getInstance().getCursor().setSelection(selection);
            }else{
                Selection selection = Model.getInstance().getCursor().getSelection();
                selection.setEnd(selection.getEnd() + 1);
            }
        }

        Model.getInstance().moveCursor(1);
    }

    public static void moveCursorUp(){//TODO: fix
        int currentLine = Model.getInstance().get_cursor_line();
        if(currentLine <= 0)
            return;

        int newLocation = Model.getInstance().getAbsolutePositionFromRelativeLine(0, currentLine - 1);

        int lineLength = Model.getInstance().getLineLength(currentLine - 1);
        if(lineLength >= Model.getInstance().getCursorRelativeLocation())
            newLocation += Model.getInstance().getCursorRelativeLocation();
        else
            newLocation += lineLength;


        Model.getInstance().setCursor(newLocation);
        //TODO: Move selection end
    }

    public static void moveCursorDown(){
        ArrayList<Integer> breaks = Model.getInstance().getBreaks();
        int currentLine = Model.getInstance().get_cursor_line();
        if((breaks.size() -1) <= currentLine)
            return;

        int currentRelativeLoc = Model.getInstance().getCursorRelativeLocation();

        if(currentRelativeLoc >= (breaks.get(currentLine + 1) - breaks.get(currentLine)))
            Model.getInstance().setCursor( breaks.get(currentLine + 1) );
        else
            Model.getInstance().setCursor(breaks.get(currentLine) + currentRelativeLoc + 1);

        //TODO: Move selection end
    }

    public static void moveCursor(double x, double y){
        Model.getInstance().getCursor().moveToXYLocation(x, y);
        Model.getInstance().getViewer().update();
        Model.getInstance().getCursor().setSelection(null);
    }

    public static void handleSelect(MouseEvent event){
        int position = Model.getInstance().getCursor().translateXYLocation(event.getX(), event.getY(), false);

        System.out.println(position);

        if( Model.getInstance().getCursor().getSelection() == null  )
            Model.getInstance().getCursor().setSelection(new Selection(position));
        else
            Model.getInstance().getCursor().getSelection().setEnd(position);
    }

    public static void delete(boolean forwards) {
        int relativeLoc = Model.getInstance().getCursorRelativeLocation();
        if(Model.getInstance().get_cursor_line() <= 0 && relativeLoc <= 0)
            return;

        if(relativeLoc == 0)
            Model.getInstance().deleteLine();
        else
            Model.getInstance().delete_text(forwards);

        Model.getInstance().moveCursor(-1);
    }
}
