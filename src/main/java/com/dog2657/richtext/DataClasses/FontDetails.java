package com.dog2657.richtext.DataClasses;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontDetails {
    private Font object;
    private int fontHeight = 15;
    private int lineGap = 1;

    private void reloadObject(){
        object = new Font("Andale Mono", this.fontHeight);
    }

    public FontDetails(int fontHeight){
        this.fontHeight = fontHeight;
        reloadObject();
    }

    public FontDetails(int fontHeight, int lineGap){
        this.fontHeight = fontHeight;
        this.lineGap = lineGap;
        reloadObject();
    }

    public Font get(){
        return this.object;
    }

    public double getCharacterWidth(){
        Text text = new Text("A");
        text.setFont(this.object);
        return text.getBoundsInLocal().getWidth();
    }

    public int getLineSpacing(){
        return this.fontHeight + this.lineGap;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
        reloadObject();
    }

    public int getLineGap() {
        return lineGap;
    }

    public void setLineGap(int lineGap) {
        this.lineGap = lineGap;
    }
}
