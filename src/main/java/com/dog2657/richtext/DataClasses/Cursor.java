package com.dog2657.richtext.DataClasses;

import com.dog2657.richtext.Model;
import javafx.scene.input.MouseEvent;

public class Cursor {
    private int position = 0;

    private Selection selection = null;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void movePosition(int moves){
        position += moves;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public Selection getSelection() {
        return selection;
    }



    public void moveToXYLocation(double x, double y){
        this.position = translateXYLocation(x, y);
    }

    public int translateXYLocation(double x, double y){
        int cursorPos = 0;
        int line = 0;

        //Gets the line
        if(Model.getInstance().getBreaks().size() > 0) {
            line = (int) Math.floor(y / Model.getInstance().getFont().getLineSpacing());
            if (line > 0)
                cursorPos += Model.getInstance().getBreaks().get(line - 1) + 1;
        }


        int character = (int)Math.round(x / Model.getInstance().getFont().getCharacterWidth());

        if(Model.getInstance().getBreaks().size() <= 0)
            return cursorPos;

        //If the x is further than the line
        if(character > Model.getInstance().getLineLength(line)) {
            int lineEnd = Model.getInstance().getBreaks().get(line);
            if(line > 0)
                lineEnd -= Model.getInstance().getBreaks().get(line -1) + 1;

            cursorPos += lineEnd;

            return cursorPos;
        }

        cursorPos += character;

        return cursorPos;
    }
}
