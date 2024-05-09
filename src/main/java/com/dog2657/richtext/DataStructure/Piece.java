package com.dog2657.richtext.DataStructure;


public class Piece {
    private int start;
    private int length;
    private Sources source;


    public Piece(int start, int length, Sources source){
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

    public Sources getSource() {
        return source;
    }

    public Piece clone(){
        return new Piece(this.start, this.length, this.source);
    }

    /**
     * Modify current piece while returning the other split piece
     *
     * @param point is the end of the original piece & the start of the new piece
     * @return
     */
    public Piece split(int point){
        Piece instance = this.clone();

        assert 0 <= point && point <= this.length : "Point is outside of this piece";

        instance.length -= point;
        instance.start = this.start + point;

        this.length = point;

        return instance;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "start=" + start +
                ", length=" + length +
                ", source='" + source + '\'' +
                '}';
    }
}
