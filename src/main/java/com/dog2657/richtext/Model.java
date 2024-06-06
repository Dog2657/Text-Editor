package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.DataStructure;
import com.dog2657.richtext.DataStructure.FontDetails;
import com.dog2657.richtext.DataStructure.Piece;
import com.dog2657.richtext.DataStructure.Sources;

import java.util.ArrayList;
import java.util.LinkedList;

public class Model {
    private static Model instance;
    private Viewer viewer;

    final private FontDetails font = new FontDetails(15);
    private String fileLocation;

    private DataStructure data = new DataStructure("");


    private ArrayList<Integer> breaks = new ArrayList<>();

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


    public void clear_data(){
        this.cursor = 0;
        this.fileLocation = null;
        this.viewer = null;
    }

    public void load_file(String data_original){
        //Multithreading break checks
        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                breaks.clear();
                char[] content = data_original.toCharArray();

                for (int i=0; i<content.length; i++) {
                    if((int)content[i] != 10)
                        continue;
                    breaks.add(i);
                }
            }
        });
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
        for (int i=0; i<breaks.size(); i++) {
            if(breaks.get(i) < cursor)
                continue;
            return i;
        }
        return 0;
    }


    public int get_cursor_relative_location(){
        int line = get_cursor_line();
        if(line == 0)
            return cursor;

        int abs = breaks.get(line -1);
        return cursor - abs - 1;
    }


    /*
     * Gets the line that a position is on
     */
    public int getLine(int position){
        for (int i=0; i<breaks.size(); i++) {
            if(breaks.get(i) < position)
                continue;
            return i;
        }
        return 0;
    }

    public ArrayList<Integer> getBreaks() {
        return breaks;
    }



    public interface processLineCallback { void process(int line, String content); }

    public void process_each_line_output(processLineCallback callback){
        String text = this.data.getOutput();
        ArrayList<Integer> lines = this.breaks;

        if(lines.size() <= 0) {
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

    public static void moveCursor(double x, double y){
        final int lineGap = 1;
        final int fontSize = 15;

        int line = (int)Math.floor(y / (fontSize + lineGap));
        int character = (int)Math.round(x / Model.getInstance().getFont().getCharacterWidth());


        System.out.println(String.format("X: %f | Y: %f", x, y));
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
        if(breaks.size() == 0)
            return this.data.getLength();

        if(line == breaks.size())
            return this.data.getLength() - (breaks.get(breaks.size() -1) + 1);

        if(line == 0)
            return breaks.get(0);

        int start = breaks.get(line -1);
        int end = breaks.get(line);

        return end - start -1;
    }

    /**
     * Shifts all line breaks after position by moves
     */
    public void shiftPoints(int moves){
        int line = getLine(cursor);
        for (int i=line; i<breaks.size(); i++) {
            int instance = breaks.get(i);
            instance += moves;
            breaks.set(i, instance);
        }
    }

    public void newLine(){
        int currentLine = get_cursor_line();
        this.add_text("\n");
        this.breaks.add(currentLine, cursor);
        update();
    }

    public void deleteLine(){
        int currentLine = get_cursor_line();
        this.delete_text(false);
        this.breaks.remove(currentLine -1);
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
