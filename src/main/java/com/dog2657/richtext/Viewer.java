package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.LineBreaks;
import com.dog2657.richtext.DataStructure.Piece;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;


public class Viewer extends Canvas {
    int fontSize;
    double fontWidth;
    Font font;

    public Viewer(int width, int height){
        super(width, height);

        fontSize = 15;
        font = new Font("Andale Mono", fontSize);
        fontWidth = getFontWidth(font);

        this.render();
    }


    private void render(){
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Color.rgb(31, 32, 32));
        gc.fillRect(0,0, this.getWidth(), this.getHeight());

        gc.setFont(font);
        gc.setFill(Color.WHITESMOKE);

        gc.fillText(Model.getInstance().get_text_output(), 0, 15);

        gc.setStroke(Color.RED);

        Location cursorLoc = getCursorLocation();
        gc.strokeLine(cursorLoc.x, cursorLoc.y, cursorLoc.x, cursorLoc.y + fontSize);
    }

    public void update(){
        this.render();
    }


    private double getFontWidth(Font font){
        Text text = new Text("A");
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
    }

    public Location getCursorLocation(){
        int line = LineBreaks.getInstance().getLine(Model.getInstance().getCursor());
        int loc = Model.getInstance().getCursor();
        double fontWidth = getFontWidth(font);

        if(line > 0){
            int lastBreak = LineBreaks.getInstance().getBreaks().get(line -1);
            loc -= lastBreak + 1;
        }

        return new Location(fontWidth * loc, line * fontSize);
    }


}
