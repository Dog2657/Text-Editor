package com.dog2657.richtext.DataStructure;

import java.util.LinkedList;

public class DataStructure {
    private LinkedList<Piece> pieces = new LinkedList<>();

    private String original = "";
    private String add = "";

    private int length = 0;

    public DataStructure(String content){
        this.original = content;
        this.pieces.add(new Piece(0, content.length(), Sources.original));
        this.length = content.length();
    }

    public String getOutput(){
        String output = "";

        for (Piece piece:this.pieces) {
            if(piece.getSource() == Sources.original){
                output += this.original.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }else if(piece.getSource() == Sources.add){
                output += this.add.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }
        }

        return output;
    }


    public void add_text(int cursor, String text){
        //The location in all pieces
        int absolute_location = 0;

        Piece selected = null;
        int selected_index = 0;

        for (int i=0; i<this.pieces.size(); i++){
            selected = this.pieces.get(i);
            if(absolute_location + selected.getLength() >= cursor) {
                selected_index = i;
                break;
            }

            absolute_location += selected.getLength();
        }

        //The position within the selected data Piece
        int relative_location = cursor - absolute_location;

        //The start of the new text in add source
        int start = this.add.length();
        this.add += text;

        //The new data Piece
        Piece data = new Piece(start, text.length(), Sources.add);

        if(relative_location <= 0) {//Is before selected piece
            this.pieces.add(selected_index, data);

        }else if (relative_location >= selected.getLength()) {//Is after selected piece

            //Checks if it can be combined with selected when source is add (For key presses)
            if(selected.getSource() == Sources.add && (this.add.length() - selected.getLength() - 1) == selected.getStart()){
                selected.setLength(selected.getLength() + 1);
            }else{
                this.pieces.add(selected_index + 1, data);
            }

        }else{//Is inside selected piece
            Piece ending_piece = selected.split(relative_location);
            this.pieces.add(selected_index + 1, data);
            this.pieces.add(selected_index + 2, ending_piece);
        }

        length += text.length();
    }

    public void delete_text(int cursor){
        //The location in all pieces
        int absolute_location = 0;

        Piece selected = null;
        int selected_index = 0;

        for (int i=0; i<this.pieces.size(); i++){
            selected = this.pieces.get(i);
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
            this.pieces.add(selected_index + 1, ending_piece);
        }

        this.length -= moves;
    }


    public LinkedList<Piece> getPieces() {
        return pieces;
    }

    public int getLength() {
        return length;
    }
}
