package com.dog2657.richtext;

public class Model {
    //TODO: Add dynamic buffer size
    int bufferSize = 1024;

    char[] buffer = new char[bufferSize];

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

    public char[] getBuffer(){
        return buffer;
    }

    public void setViewer(Viewer viewer){
        this.viewer = viewer;
    }
}
