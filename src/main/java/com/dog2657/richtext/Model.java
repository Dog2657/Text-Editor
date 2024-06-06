package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.Piece;
import com.dog2657.richtext.DataStructure.Sources;

import java.util.ArrayList;
import java.util.LinkedList;

public class Model {
    private static Model instance;
    private Viewer viewer;

    private String fileLocation;

    private String data_original = "";
    private String data_add = "";

    private LinkedList<Piece> data_pieces = new LinkedList<>();
    private int file_total_length = 0;

    private ArrayList<Integer> breaks = new ArrayList<>();

    private int cursor = 0;

    public static Model getInstance() {
        if(instance == null)
            instance = new Model();
        return instance;
    }

    private Model(){ }

    public  LinkedList<Piece> get_data_pieces() {
        return data_pieces;
    }


    public void clear_data(){
        this.data_original = "";
        this.data_add = "";
        this.data_pieces = new LinkedList<>();

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

        this.reset();
        this.data_original = data_original;
        this.data_pieces.clear();
        this.data_pieces.add(new Piece(0, data_original.length(), Sources.original));
        this.file_total_length = data_original.length();
        this.cursor = 0;

        try{//Waits for line breaks to finish
            t.join();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        update();
    }

    private void reset(){
        this.data_add = "";
        this.data_pieces = new LinkedList<>();
        this.file_total_length = 0;
        this.data_original = "";
    }

    public String get_text_output(){
        String output = "";

        for (Piece piece:this.data_pieces) {
            if(piece.getSource() == Sources.original){
                output += this.data_original.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }else if(piece.getSource() == Sources.add){
                output += this.data_add.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }
        }
        return output;
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
        String text = Model.getInstance().get_text_output();
        ArrayList<Integer> lines = this.breaks;

        if(lines.size() <= 0)
            return;

        String content = text.substring(0, lines.get(0));
        callback.process(0, content);

        for (int i=1; i<lines.size(); i++) {
            int start = lines.get(i-1) + 1;
            int end = lines.get(i);
            content = text.substring(start, end);

            callback.process(i, content);
        }

        content = text.substring(lines.get(lines.size() -1) +1, this.file_total_length);
        callback.process(lines.size() -1, content);
    }


    public void add_text(String text){
        //The location in all pieces
        int absolute_location = 0;

        Piece selected = null;
        int selected_index = 0;

        for (int i=0; i<this.data_pieces.size(); i++){
            selected = this.data_pieces.get(i);
            if(absolute_location + selected.getLength() >= cursor) {
                selected_index = i;
                break;
            }

            absolute_location += selected.getLength();
        }

        //The position within the selected data Piece
        int relative_location = cursor - absolute_location;

        //The start of the new text in add source
        int start = this.data_add.length();
        this.data_add += text;

        //The new data Piece
        Piece data = new Piece(start, text.length(), Sources.add);

        if(relative_location <= 0) {//Is before selected piece
            this.data_pieces.add(selected_index, data);

        }else if (relative_location >= selected.getLength()) {//Is after selected piece

            //Checks if it can be combined with selected when source is add (For key presses)
            if(selected.getSource() == Sources.add && (this.data_add.length() - selected.getLength() - 1) == selected.getStart()){
                selected.setLength(selected.getLength() + 1);
            }else{
                this.data_pieces.add(selected_index + 1, data);
            }

        }else{//Is inside selected piece
            Piece ending_piece = selected.split(relative_location);
            this.data_pieces.add(selected_index + 1, data);
            this.data_pieces.add(selected_index + 2, ending_piece);
        }



        this.shiftPoints(1);
        file_total_length += text.length();
        update();
    }


    public void delete_text(boolean forwards){
        //The location in all pieces
        int absolute_location = 0;

        Piece selected = null;
        int selected_index = 0;

        for (int i=0; i<this.data_pieces.size(); i++){
            selected = this.data_pieces.get(i);
            if(absolute_location + selected.getLength() >= cursor) {
                selected_index = i;
                break;
            }

            absolute_location += selected.getLength();
        }

        //The position within the selected data Piece
        int relative_location = cursor - absolute_location;

        if(selected_index <= 0 && relative_location <= 0)
            return;

        final int moves = 1;

        if(relative_location <= 0) {
            selected.setStart(selected.getStart() + moves);
        }else if (relative_location >= selected.getLength()) {//Is after selected piece
            selected.setLength(selected.getLength() - moves);
        }else{//Is inside selected piece
            Piece ending_piece = selected.split(relative_location);
            selected.setLength(selected.getLength() - moves);
            data_pieces.add(selected_index + 1, ending_piece);
        }

        this.shiftPoints(-1);
        file_total_length -= moves;
        update();
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
        else if (cursor > file_total_length)
            cursor = file_total_length;

        update();
    }

    public char[] getBuffer(){
        return null;
    }

    private void update(){
        if(this.viewer != null)
            this.viewer.update();
    }

    public void setViewer(Viewer viewer){
        this.viewer = viewer;
    }
}
