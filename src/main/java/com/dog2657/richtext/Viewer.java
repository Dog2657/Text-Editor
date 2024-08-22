package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.Selection;
import com.dog2657.richtext.exceptions.SelectionEmptyException;
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

        final boolean[] selectionStyledText = {false};

        Model.getInstance().process_each_line_output((int line, String content, int previousLinesTotal) -> {
            double lineY = (line * lineGap);

            try {
                Selection selection = Model.getInstance().getCursor().getSelection();

                double backgroundWidth = content.length() * Model.getInstance().getFont().getCharacterWidth();
                double horizontalStart = 0;



                //Check if drawing line which is partially selected
                if( (!selectionStyledText[0]) && ( selection.getBeginning() <= (previousLinesTotal + content.length())) ){
                    int charOffset = selection.getBeginning() - previousLinesTotal;

                    horizontalStart = charOffset * Model.getInstance().getFont().getCharacterWidth();
                    backgroundWidth -= horizontalStart;

                    selectionStyledText[0] = true;
                }

                boolean isSelectionEnd =  (selectionStyledText[0]) && (selection.getEnding() <= (previousLinesTotal + content.length()) );

                //Checks for the end of the selection
                if(isSelectionEnd){
                    int charOffset = (previousLinesTotal + content.length()) - selection.getEnding();
                    backgroundWidth -= charOffset * Model.getInstance().getFont().getCharacterWidth();
                }


                //Render selection background
                if(selectionStyledText[0]){
                    gc.setFill(Color.RED);
                    gc.fillRect(horizontalStart, lineY + 3,
                            backgroundWidth,
                            Model.getInstance().getFont().getFontHeight()
                    );
                }

                //Checks for the end of the selection
                if(isSelectionEnd)
                    selectionStyledText[0] = false;
            }catch(SelectionEmptyException error){}

            gc.setFill(Color.WHITE);
            gc.fillText(content, 0, lineY + 15);

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
        double fontWidth = Model.getInstance().getFont().getCharacterWidth();

        int loc = Model.getInstance().getCursorPosition();
        if(line > 0)
            loc = Model.getInstance().getCursorRelativeLocation();

        return new Location(fontWidth * loc, line * Model.getInstance().getFont().getLineSpacing());
    }


}
