package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.Piece;
import com.dog2657.richtext.DataStructure.Sources;

import java.util.Arrays;
import java.util.LinkedList;

public class Model {

    private static Model instance;
    private Viewer viewer;

    private String fileLocation;

    private String data_original = "";
    private String data_add = "";

    private LinkedList<Piece> data_pieces = new LinkedList<>();
    private int file_total_length = 0;

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
        this.data_original = data_original;
        this.add_text("");
        this.data_pieces.clear();
        this.data_pieces.add(new Piece(0, data_original.length(), Sources.original));
        this.file_total_length = data_original.length();
        this.cursor = 0;
        update();
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

        file_total_length -= moves;
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
