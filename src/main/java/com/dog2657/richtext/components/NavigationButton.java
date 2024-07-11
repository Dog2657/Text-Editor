package com.dog2657.richtext.components;

import com.dog2657.richtext.Navigation;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

public class NavigationButton extends Button {
    public NavigationButton(String name){
        super(name);

        this.setFocusTraversable(false);
        this.setStyle(
                // Set styles for padding, border, background color, text fill, cursor, and corner radius
                "-fx-padding: 2px 8px; " +
                "-fx-border-width: 0px; " +
                "-fx-background-color: #4d4c4c; " +  // Dark grey background color
                "-fx-text-fill: white; " +
                "-fx-cursor: hand !important; " +
                "-fx-background-radius: 2px;" //+
                //"-fx-background-insets: 8px;"
        );

    }
}
