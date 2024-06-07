package com.dog2657.richtext.DataClasses;

import java.util.ArrayList;

public class BreakPoints {
    private ArrayList<Integer> points = new ArrayList<>();

    public BreakPoints(){}

    public BreakPoints(String content){
        this.parse(content);
    }

    public void parse(String content){
        char[] chars = content.toCharArray();

        for (int i=0; i<chars.length; i++) {
            if((int)chars[i] != 10)
                continue;
            points.add(i);
        }
    }

    /**
     * Gets the line which a position is on
     * @param position the location within the entire file like the cursor
     * @return {int} line which the position is on.
     */
    public int getPositionLine(int position){
        for (int i=0; i<points.size(); i++) {
            if(points.get(i) < position)
                continue;
            return i;
        }
        return 0;
    }

    /**
     *  Gets the relative position from the start of the line
     *
     * @example Line starts at 50 & point is at 55 will return 5
     * @param position is the abslute location in the file like a cursor
     * @return the position relative to the line
     */
    public int getRelativeLineLocation(int position, int line){
        return position - this.points.get(line);
    }

    /**
     *  Gets the relative position from the start of the line
     *
     * @example Line starts at 50 & point is at 55 will return 5
     * @param position is the abslute location in the file like a cursor
     * @return the position relative to the line
     */
    public int getRelativeLineLocation(int position){
        int line = getPositionLine(position);
        return getRelativeLineLocation(position, line);
    }

    /**
     * Shifts all line breaks after position by moves
     * @param position the start point
     * @param moves the amount to shift each point
     */
    public void shiftPoints(int position, int moves){
        int line = this.getPositionLine(position);
        for (int i=line; i<this.points.size(); i++) {
            int instance = this.points.get(i);
            instance += moves;
            this.points.set(i, instance);
        }
    }

    public int getLineLength(int line, int fileLength){
        if(this.points.size() == 0)
            return fileLength;

        if(line == this.points.size())
            return fileLength - (points.get(this.points.size() -1) + 1);

        if(line == 0)
            return this.points.get(0);

        int start = this.points.get(line -1);
        int end = this.points.get(line);

        return end - start -1;
    }


    public void newPoint(int index, int position){
        this.points.add(index, position);
    }

    public void deletePoint(int index){
        this.points.remove(index);
    }

    public int getPoint(int index){
        return this.points.get(index);
    }

    public int size(){
        return this.points.size();
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }
}
