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

    public void parse(String text){
        breaks.clear();
        char[] content = text.toCharArray();

        for (int i=0; i<content.length; i++) {
            if((int)content[i] != 10)
                continue;
            breaks.add(i);
        }
    }

    public int getLine(int cursor){
        for (int i=0; i<breaks.size(); i++) {
            if(breaks.get(i) < cursor)
                continue;
            return i;
        }
        return 0;
    }

    public ArrayList<Integer> getBreaks() {
        return breaks;
    }
}
