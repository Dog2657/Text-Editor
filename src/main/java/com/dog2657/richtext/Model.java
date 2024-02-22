package com.dog2657.richtext;

public class Model {
    //TODO: Add dynamic buffer size
    private int bufferSize = 1024;

    private char[] buffer = new char[bufferSize];

    private int cursor = 3;

    private static Model instance;
    private Viewer viewer;

    public static Model getInstance() {
        if(instance == null)
            instance = new Model();
        return instance;
    }

    private Model(){ }

    public void setBuffer(char[] buffer){
        this.buffer = buffer;
        viewer.update();
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
