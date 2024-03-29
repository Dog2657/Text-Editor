package com.dog2657.richtext;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



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
        gc.fillText(new String(Model.getInstance().getBuffer()), 0, 15);

        gc.setStroke(Color.RED);

        Location cursorLoc = getCursorLocation();
        gc.strokeLine(cursorLoc.x, 0, cursorLoc.x, fontSize);
    }

    public void update(){
        render();
    }


    private double getFontWidth(Font font){
        Text text = new Text("A");
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
    }

    public Location getCursorLocation(){
        double fontWidth = getFontWidth(font);

        return new Location(fontWidth * Model.getInstance().getCursor(), 0);
    }


}
