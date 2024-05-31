package com.dog2657.richtext.DataStructure;

import java.util.ArrayList;

public class LineBreaks {
    private static LineBreaks instance;
    private ArrayList<Integer> breaks = new ArrayList<>();

    public static LineBreaks getInstance(){
        if(instance == null)
            instance = new LineBreaks();
        return instance;
    }

    private LineBreaks(){}

    /**
     * Goes through the text to find all line breaks
     */
    public void parse(String text){
        breaks.clear();
        char[] content = text.toCharArray();

        for (int i=0; i<content.length; i++) {
            if((int)content[i] != 10)
                continue;
            breaks.add(i);
        }
    }

    /*
    * Gets the line that a position is on
     */
    public int getLine(int position){
        for (int i=0; i<breaks.size(); i++) {
            if(breaks.get(i) < position)
                continue;
            return i;
        }
        return 0;
    }

    static public int getRelativeLocation(int position, int line){
        int lastBreak = LineBreaks.getInstance().getBreaks().get(line -1);
        return position - (lastBreak + 1);
    }

    static public int getRelativeLocation(int position) {
        int line = LineBreaks.getInstance().getLine(position);
        return getRelativeLocation(position, line);
    }

    public ArrayList<Integer> getBreaks() {
        return breaks;
    }
}
