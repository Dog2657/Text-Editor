package com.dog2657.richtext.DataStructure;

public class Piece {
    private int start;
    private int length;
    private String source;


    public Piece(int start, int length, String source){
        this.start = start;
        this.length = length;
        this.source = source;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return length;
    }

    public String getSource() {
        return source;
    }
}
