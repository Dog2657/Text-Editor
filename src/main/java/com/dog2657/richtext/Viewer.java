package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.LineBreaks;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Viewer extends Canvas {
    int fontSize;
    double fontWidth;
    double lineGap;
    Font font;

    public Viewer(int width, int height){
        super(width, height);

        fontSize = 15;
        lineGap = 1;
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

        String text = Model.getInstance().get_text_output();
        String[] lines = text.split("\n");

        double lineGap = fontSize + this.lineGap;

        for (int i=0; i<lines.length; i++) {
            gc.fillText(lines[i], 0, (i * lineGap) + 15);
        }

        //gc.fillText(text, 0, 15);

        gc.setStroke(Color.RED);

        Location cursorLoc = getCursorLocation();
        gc.strokeLine(cursorLoc.x, cursorLoc.y + 5, cursorLoc.x, cursorLoc.y + fontSize);
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
            //int lastBreak = LineBreaks.getInstance().getBreaks().get(line -1);
            loc = LineBreaks.getRelativeLocation(loc);
        }

        return new Location(fontWidth * loc, line * (fontSize + lineGap));
    }


}
