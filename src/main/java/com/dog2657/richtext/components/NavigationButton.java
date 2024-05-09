package com.dog2657.richtext.components;

import com.dog2657.richtext.Navigation;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class NavigationButton extends Button {

    public NavigationButton(String name){
        super(name);

        this.setFocusTraversable(false);
        this.setStyle("-fx-border-width: 0px; -fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand !important; -fx-padding: 0px;");
    }
}
