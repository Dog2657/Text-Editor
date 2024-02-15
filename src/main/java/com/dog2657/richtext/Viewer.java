package com.dog2657.richtext;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Viewer extends Canvas {
    char[] buffer;
    int fontSize;
    double fontWidth;
    Font font;

    int cursor = 3;

    public Viewer(int width, int height){
        super(width, height);

        buffer = FileManager.readFile("src/main/resources/test.txt");

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
        gc.fillText(new String(buffer), 0, 15);



        gc.setStroke(Color.RED);


        Location cursorLoc = getCursorLocation();
        gc.strokeLine(cursorLoc.x, 0, cursorLoc.x, fontSize);

        System.out.println(150 * fontWidth);
    }


    private double getFontWidth(Font font){
        Text text = new Text("A");
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
    }

    public Location getCursorLocation(){
        double fontWidth = getFontWidth(font);

        return new Location(fontWidth * cursor, 0);
    }

}
