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


    public String get_data_original_text() {
        return data_original;
    }

    public String get_data_added_text() {
        return data_add;
    }

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

    public void set_data_original(String data_original){
        this.data_original = data_original;
        this.data_pieces.add(new Piece(0, data_original.length(), Sources.original));
        this.file_total_length = data_original.length();
    }

    public String get_text_output(){
        String output = "";


        for (Piece piece:this.data_pieces) {

            if(piece.getSource() == Sources.original){

                int test = piece.getStart() + piece.getLength();

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

        //3 cases



        if(relative_location <= 0){//At the start of the piece

            int start = this.data_add.length();
            this.data_add += text;
            Piece data = new Piece(start, text.length(), Sources.add);
            this.data_pieces.add(selected_index, data);
            System.out.println("Add at start");

        } else if (relative_location >= selected.getLength()) {

            int start = this.data_add.length();
            this.data_add += text;
            Piece data = new Piece(start, text.length(), Sources.add);
            this.data_pieces.add(selected_index+1, data);
            System.out.println("Add to end");

        }else{
            System.out.println("Add inside");

            int start = this.data_add.length();
            this.data_add += text;
            Piece data = new Piece(start, text.length(), Sources.add);

            Piece ending_piece = selected.split(relative_location);
            this.data_pieces.add(selected_index + 1, data);
            this.data_pieces.add(selected_index + 2, ending_piece);
        }

        //At the end of the piece
        //Inside the piece

        /*if(cursor <= 0 || cursor >= file_total_length){
            int start = this.data_add.length();
            this.data_add += text;

            Piece data = new Piece(start, text.length(), Sources.add);

            if(cursor <= 0)
                this.data_pieces.add(0, data);

            else if(cursor >= file_total_length)
                this.data_pieces.add(data);

        }else{
            int start = this.data_add.length();
            this.data_add += text;

            Piece start_piece = this.data_pieces.get(0);
            int N = 0;
            int position = 0;

            for (int i=0; i<this.data_pieces.size(); i++) {
                if(N + start_piece.getLength() >= cursor)
                    break;

                start_piece = this.data_pieces.get(i);
                N += start_piece.getLength();
                position = i;
            }

            int n = cursor - N;

            Piece data = new Piece(start, text.length(), Sources.add);

            Piece end_piece = start_piece.split(n);
            end_piece.setLength(n);
            end_piece.setStart(end_piece.getStart() + n);

            start_piece.setLength(n);

            this.data_pieces.add(position+1, data);
            this.data_pieces.add(position+2, end_piece);
        }*/

        file_total_length += text.length();
    }


    public void delete_text(){

    }

    public void setBuffer(char[] buffer){
        /*try{
            data_original = new String(buffer);
            data_add = "";

            Arrays.fill(data_pieces, null);
            data_pieces[0] =new Piece(0, data_original.length(), "original");

            viewer.update();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }


        //TODO: Remove
        this.buffer = buffer;*/
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
