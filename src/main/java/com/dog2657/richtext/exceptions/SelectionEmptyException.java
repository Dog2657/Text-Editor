package com.dog2657.richtext.exceptions;

public class SelectionEmptyException extends Exception{
    public SelectionEmptyException(){
        super("Unable to get selection due to it being empty");
    }
}
