package com.dog2657.richtext;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Viewer extends Canvas {
    char[] buffer = FileManager.readFile("/Users/reece/Documents/1.Coding_Projects/Java/Rich Text/src/main/resources/test.txt");
    int cursorLocation = 0;

    public Viewer(int width, int height){
        super(width, height);

        this.render();
    }

    private void render(){
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Color.rgb(31, 32, 32));
        gc.fillRect(0,0, this.getWidth(), this.getHeight());

        Font font = new Font("Andale Mono", 15);

        gc.setFont(font);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(new String(buffer), 0, 15);



        gc.setStroke(Color.RED);
        double fontWidth = getFontWidth(font);
        gc.strokeLine(fontWidth * 3, 0, fontWidth * 3, 15);
    }


    private double getFontWidth(Font font){
        Text text = new Text("A");
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
    }




}
