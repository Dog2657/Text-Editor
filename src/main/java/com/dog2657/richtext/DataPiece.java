package com.dog2657.richtext;

public class DataPiece {
    private int start;
    private int length;
    private String source ;

    public DataPiece(int start, int length, String source) throws Exception {
        if(source != "add" && source != "original")
            throw new Exception("Invalid source to create a data piece from");

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
