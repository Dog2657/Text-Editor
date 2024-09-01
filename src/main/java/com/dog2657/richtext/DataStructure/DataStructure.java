package com.dog2657.richtext.DataStructure;

import java.util.ArrayList;
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
        piece_processing_info info = get_piece_processing_info(cursor);

        //The start of the new text in add source
        int start = this.add.length();
        this.add += text;

        //The new data Piece
        Piece data = new Piece(start, text.length(), Sources.add);

        if(info.relative_location <= 0) {//Is before selected piece
            this.pieces.add(info.selected_index, data);

        }else if (info.relative_location >= info.selected.getLength()) {//Is after selected piece

            //Checks if it can be combined with selected when source is add (For key presses)
            if(info.selected.getSource() == Sources.add && (this.add.length() - info.selected.getLength() - 1) == info.selected.getStart()){
                info.selected.setLength(info.selected.getLength() + 1);
            }else{
                this.pieces.add(info.selected_index + 1, data);
            }

        }else{//Is inside selected piece
            Piece ending_piece = info.selected.split(info.relative_location);
            this.pieces.add(info.selected_index + 1, data);
            this.pieces.add(info.selected_index + 2, ending_piece);
        }

        length += text.length();
    }

    public void delete_text(int cursor){
        piece_processing_info info = get_piece_processing_info(cursor);

        if(info.selected_index <= 0 && info.relative_location <= 0)
            return;

        final int moves = 1;

        if(info.relative_location <= 0) {
            info.selected.setStart(info.selected.getStart() + moves);
        }else if (info.relative_location >= info.selected.getLength()) {//Is after selected piece
            info.selected.setLength(info.selected.getLength() - moves);
        }else{//Is inside selected piece
            Piece ending_piece = info.selected.split(info.relative_location);
            info.selected.setLength(info.selected.getLength() - moves);
            this.pieces.add(info.selected_index + 1, ending_piece);
        }

        this.length -= moves;
    }


    public void delete_text_new(int deletion_start, int deletion_end){
        int absolute_start = 0;

        LinkedList<Piece> included_pieces = new LinkedList<>();

        for (int i=0; i<this.pieces.size(); i++) {
            Piece instance = this.pieces.get(i);

            if(absolute_start <= deletion_start && deletion_start <= (absolute_start + instance.getLength())){
                int relative_start = deletion_start - absolute_start;
                if(relative_start <= 0) {
                    absolute_start += instance.getLength();
                    continue;
                }

                Piece removed = instance.split(relative_start);
                included_pieces.add(instance);
                instance = removed;
                absolute_start += relative_start;
            }


            //Checks if piece is completely outside the deletion range
            if(absolute_start < deletion_start || deletion_end <= absolute_start){
                absolute_start += instance.getLength();
                included_pieces.add(instance);
                continue;
            }


            if(absolute_start <= deletion_end && deletion_end <= (absolute_start + instance.getLength())){
                int relative_end = (absolute_start + instance.getLength()) - deletion_end;
                if(relative_end <= 0){
                    absolute_start += instance.getLength();
                    continue;
                }

                Piece new_instance = instance.split(instance.getLength() - relative_end);
                included_pieces.add(new_instance);
                absolute_start += instance.getLength();

                continue;
            }

            absolute_start += instance.getLength();
        }

        this.pieces = included_pieces;
    }


    public LinkedList<Piece> getPieces() {
        return pieces;
    }

    public int getLength() {
        return length;
    }

    public int getPiecesSize(){ return this.pieces.size(); }



    /* ---------------------------- DRY Code below  ---------------------------- */

    private class piece_processing_info{
        public int absolute_location;
        public int relative_location;
        public int selected_index;
        public Piece selected;


        private piece_processing_info(int absolute_location, int relative_location, int selected_index, Piece selected) {
            this.absolute_location = absolute_location;
            this.relative_location = relative_location;
            this.selected_index = selected_index;
            this.selected = selected;
        }

    }

    private piece_processing_info get_piece_processing_info(int cursor){
        //The location in all pieces
        int absolute_location = 0;

        Piece selected = null;
        int selected_index = 0;

        for (int i=0; i<pieces.size(); i++){
            selected = pieces.get(i);
            if(absolute_location + selected.getLength() >= cursor) {
                selected_index = i;
                break;
            }

            absolute_location += selected.getLength();
        }

        //The position within the selected data Piece
        int relative_location = cursor - absolute_location;

        return new piece_processing_info(absolute_location, relative_location, selected_index, selected);
    }
}
