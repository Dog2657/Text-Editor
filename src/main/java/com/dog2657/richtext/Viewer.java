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


        String original = Model.getInstance().get_data_original_text();
        String added = Model.getInstance().get_data_added_text();
        DataPiece[] pieces = Model.getInstance().get_data_pieces();



        for (int i=0; i<pieces.length; i++) {
            DataPiece piece = pieces[i];
            if(piece == null)
                break;

            String text = "";

            if(piece.getSource() == "original")
                text = original.substring(piece.getStart(), piece.getStart() + piece.getLength());
            else if(piece.getSource() == "add")
                text  = added.substring(piece.getStart(), piece.getStart() + piece.getLength());


            //TODO: Move text position according to end of last text
            gc.fillText(text, 0, 15);

        }

        //gc.fillText(new String(Model.getInstance().getBuffer()), 0, 15);

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
