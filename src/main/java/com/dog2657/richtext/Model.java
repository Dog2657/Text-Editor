package com.dog2657.richtext;

import com.dog2657.richtext.DataStructure.Piece;

import java.util.Arrays;
import java.util.LinkedList;

public class Model {

    private static Model instance;
    private Viewer viewer;

    private String fileLocation;

    private String data_original = "";
    private String data_add = "";
    private LinkedList<Piece> data_pieces = new LinkedList<>();

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
        this.data_pieces.add(new Piece(0, data_original.length(), "original"));
    }

    public String get_text_output(){
        String output = "";
        for (Piece piece:this.data_pieces) {

            if(piece.getSource() == "original"){
                output += this.data_original.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }else if(piece.getSource() == "add"){
                output += this.data_add.substring(piece.getStart(), piece.getStart() + piece.getLength());
            }
        }
        return output;
    }

    public void add_text(String text){
        if(cursor <= 0){
            this.data_pieces.add(new Piece(cursor, data_original.length(), "add"));
        }

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
