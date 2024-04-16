package com.dog2657.richtext;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Model {

    private String data_original = "";
    private String data_add = "";
    private DataPiece[] data_pieces = new DataPiece[5];


    public String get_data_original_text() {
        return data_original;
    }

    public String get_data_added_text() {
        return data_add;
    }

    public DataPiece[] get_data_pieces() {
        return data_pieces;
    }






















    //TODO: Add dynamic buffer size
    private int bufferSize = 1024;

    private String fileLocation;
    private char[] buffer = new char[bufferSize];

    private int cursor = 0;

    private static Model instance;
    private Viewer viewer;

    public static Model getInstance() {
        if(instance == null)
            instance = new Model();
        return instance;
    }

    private Model(){ }

    public void setBuffer(char[] buffer){
        try{
            data_original = new String(buffer);
            data_add = "";

            Arrays.fill(data_pieces, null);
            data_pieces[0] =new DataPiece(0, data_original.length(), "original");

            viewer.update();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }


        //TODO: Remove
        this.buffer = buffer;
    }

    public void addToBuffer(char character){
        this.buffer[++cursor] = character;
        viewer.update();
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
        this.viewer.update();
    }

    public char[] getBuffer(){
        return buffer;
    }

    public void setViewer(Viewer viewer){
        this.viewer = viewer;
    }
}
