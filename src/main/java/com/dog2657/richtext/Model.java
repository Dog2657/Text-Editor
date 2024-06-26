package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.BreakPoints;
import com.dog2657.richtext.DataClasses.Cursor;
import com.dog2657.richtext.DataClasses.FontDetails;
import com.dog2657.richtext.DataClasses.Selection;
import com.dog2657.richtext.DataStructure.DataStructure;
import com.dog2657.richtext.DataStructure.Piece;

import java.util.ArrayList;
import java.util.LinkedList;

public class Model {
    private static Model instance;
    private Viewer viewer;

    final private FontDetails font = new FontDetails(15);
    private String fileLocation;

    private DataStructure data = new DataStructure("");
    private BreakPoints breakpoints = new BreakPoints();

    public Cursor cursor = new Cursor();


    public static Model getInstance() {
        if(instance == null)
            instance = new Model();
        return instance;
    }


    private Model(){ }

    public LinkedList<Piece> get_data_pieces() {
        return this.data.getPieces();
    }


    public void load_file(String data_original){
        //Multithreading break checks
        Thread t = new Thread(() -> breakpoints.parse(data_original));
        t.start();

        this.data = new DataStructure(data_original);
        breakpoints = new BreakPoints();
        this.cursor.setPosition(0);

        try{//Waits for line breaks to finish
            t.join();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }



        //TODO: Remove code below when finished with the rendering of selections
        Selection s = new Selection(29);
        s.setEnd(155);
        this.cursor.setSelection(s);

        update();
    }


    public String get_text_output(){
        return this.data.getOutput();
    }

    public int get_cursor_line(){
        return this.breakpoints.getPositionLine(this.cursor.getPosition());
    }


    public int getCursorRelativeLocation(){
        int line = breakpoints.getPositionLine(this.cursor.getPosition());
        return breakpoints.getRelativeLineLocation(this.cursor.getPosition(), line);
    }

    public int getAbsolutePositionFromRelativeLine(int relativePoint, int line){
        return breakpoints.getAbsolutePositionFromRelativeLine(relativePoint, line);
    }

    public ArrayList<Integer> getBreaks() {
        return breakpoints.getPoints();
    }


    public interface processLineCallback { void process(int line, String content, int previousLinesTotal); }

    public void process_each_line_output(processLineCallback callback){
        String text = this.data.getOutput();
        ArrayList<Integer> lines = this.breakpoints.getPoints();

        if(lines.size() == 0) {
            callback.process(0, text, 0);
            return;
        }

        String content = text.substring(0, lines.get(0));
        callback.process(0, content, 0);

        int previousLinesTotal = content.length();

        for (int i=1; i<lines.size(); i++) {
            int start = lines.get(i-1) + 1;
            int end = lines.get(i);
            content = text.substring(start, end);

            callback.process(i, content, previousLinesTotal);

            previousLinesTotal += content.length();
        }

        content = text.substring(lines.get(lines.size() -1) +1, this.data.getLength());
        callback.process(lines.size() -1, content, previousLinesTotal);
    }

    public void add_text(String text){
        this.data.add_text(this.cursor.getPosition(), text);
        this.shiftPoints(1);
        update();
    }

    public void delete_text(boolean forwards){
        this.data.delete_text(this.cursor.getPosition());
        this.shiftPoints(-1);
        update();
    }

    public int getLineLength(int line){
        return breakpoints.getLineLength(line, data.getLength());
    }

    /**
     * Shifts all line breaks after position by moves
     */
    public void shiftPoints(int moves){
        this.breakpoints.shiftPoints(this.cursor.getPosition(), moves);
    }

    public void newLine(){
        int currentLine = this.breakpoints.getPositionLine(this.cursor.getPosition());
        this.add_text("\n");
        this.breakpoints.newPoint(currentLine, this.cursor.getPosition());
        update();
    }

    public void deleteLine(){
        int currentLine = get_cursor_line();
        this.delete_text(false);
        this.breakpoints.deletePoint(currentLine -1);
        update();
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }



    public int getCursorPosition() {
        return this.cursor.getPosition();
    }

    public void setCursor(int cursor) {
        this.cursor.setPosition(cursor);
        update();
    }

    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Moves the cursor left or right
     * @param moves the cursor takes (negative numbers move to the left & positive numbers move to the right)
     */
    public void moveCursor(int moves){
        this.cursor.movePosition(moves);

        if(this.cursor.getPosition() < 0)
            cursor.setPosition(0);
        else if (this.cursor.getPosition() > this.data.getLength())
            cursor.setPosition(this.data.getLength());

        update();
    }

    public char[] getBuffer(){
        return null;
    }

    private void update(){
        if(this.viewer != null)
            this.viewer.update();
    }

    public FontDetails getFont() {
        return font;
    }

    public void setViewer(Viewer viewer){
        this.viewer = viewer;
    }
    public Viewer getViewer() { return viewer; }
}
