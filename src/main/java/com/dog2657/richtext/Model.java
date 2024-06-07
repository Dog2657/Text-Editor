package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.BreakPoints;
import com.dog2657.richtext.DataClasses.FontDetails;
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
    private BreakPoints points = new BreakPoints();



    private int cursor = 0;

    public static Model getInstance() {
        if(instance == null)
            instance = new Model();
        return instance;
    }

    private Model(){ }

    public  LinkedList<Piece> get_data_pieces() {
        return this.data.getPieces();
    }


    public void load_file(String data_original){
        //Multithreading break checks
        Thread t = new Thread(() -> points.parse(data_original));
        t.start();

        this.data = new DataStructure(data_original);
        this.cursor = 0;

        try{//Waits for line breaks to finish
            t.join();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        update();
    }



    public String get_text_output(){
        return this.data.getOutput();
    }

    public int get_cursor_line(){
        return this.points.getPositionLine(cursor);
    }


    public int get_cursor_relative_location(){
        return points.getRelativeLineLocation(cursor);
    }



    public ArrayList<Integer> getBreaks() {
        return points.getPoints();
    }



    public interface processLineCallback { void process(int line, String content); }

    public void process_each_line_output(processLineCallback callback){
        String text = this.data.getOutput();
        ArrayList<Integer> lines = this.points.getPoints();

        if(lines.size() == 0) {
            callback.process(0, text);
            return;
        }

        String content = text.substring(0, lines.get(0));
        callback.process(0, content);

        for (int i=1; i<lines.size(); i++) {
            int start = lines.get(i-1) + 1;
            int end = lines.get(i);
            content = text.substring(start, end);

            callback.process(i, content);
        }

        content = text.substring(lines.get(lines.size() -1) +1, this.data.getLength());
        callback.process(lines.size() -1, content);
    }

    public void add_text(String text){
        this.data.add_text(this.cursor, text);
        this.shiftPoints(1);
        update();
    }


    public void delete_text(boolean forwards){
        this.data.delete_text(cursor);
        this.shiftPoints(-1);
        update();
    }

    public int getLineLength(int line){
        if(this.points.size() == 0)
            return this.data.getLength();

        if(line == this.points.size())
            return this.data.getLength() - (points.getPoint(this.points.size() -1) + 1);

        if(line == 0)
            return points.getPoint(0);

        int start = points.getPoint(line -1);
        int end = points.getPoint(line);

        return end - start -1;
    }

    /**
     * Shifts all line breaks after position by moves
     */
    public void shiftPoints(int moves){
        this.points.shiftPoints(cursor, moves);
    }

    public void newLine(){
        int currentLine = this.points.getPositionLine(cursor);
        this.add_text("\n");
        this.points.newPoint(currentLine, cursor);
        update();
    }

    public void deleteLine(){
        int currentLine = get_cursor_line();
        this.delete_text(false);
        this.points.deletePoint(currentLine -1);
        update();
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
        update();
    }

    /**
     * Moves the cursor left or right
     * @param moves the cursor takes (negative numbers move to the left & positive numbers move to the right)
     */
    public void moveCursor(int moves){
        cursor += moves;

        if(cursor < 0)
            cursor = 0;
        else if (cursor > this.data.getLength())
            cursor = this.data.getLength();

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

}
