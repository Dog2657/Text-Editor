package com.dog2657.richtext;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Viewer extends Canvas {
    public Viewer(int width, int height){
        super(width, height);

        this.render();
    }


    private void render(){
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Color.rgb(31, 32, 32));
        gc.fillRect(0,0, this.getWidth(), this.getHeight());

        gc.setFont(Model.getInstance().getFont().get());
        gc.setFill(Color.WHITESMOKE);

        double lineGap = Model.getInstance().getFont().getLineSpacing();

        Model.getInstance().process_each_line_output((int line, String content) -> {
            gc.fillText(content, 0, (line * lineGap) + 15);
        });


        gc.setStroke(Color.RED);

        Location cursorLoc = getCursorLocation();
        gc.strokeLine(cursorLoc.x, cursorLoc.y + 5, cursorLoc.x, cursorLoc.y + Model.getInstance().getFont().getFontHeight());
    }

    public void update(){
        this.render();
    }


    public Location getCursorLocation(){
        int line = Model.getInstance().get_cursor_line();
        int loc = Model.getInstance().getCursor();


        double fontWidth = Model.getInstance().getFont().getCharacterWidth();

        if(line > 0){
            loc = Model.getInstance().getCursorRelativeLocation();
            System.out.println(loc);
        }

        return new Location(fontWidth * loc, line * Model.getInstance().getFont().getLineSpacing());
    }


}
