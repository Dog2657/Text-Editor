package com.dog2657.richtext;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Navigation extends HBox {
    public Navigation(Stage primaryStage){
        this.setStyle("-fx-background-color: rgb(31, 32, 32)");

        this.getChildren().addAll(fileOpenButton(primaryStage));
    }

    private Button fileOpenButton(Stage stage){
        Button button = new Button("Add");
        button.setStyle("-fx-border-width: 0px; -fx-background-color: transparent; -fx-text-fill: white; -fx-cursor: hand !important; -fx-padding: 0px;");

        button.setOnAction(event -> {
            FileChooser chooser = new FileChooser();

            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            File file = chooser.showOpenDialog(stage);
            System.out.println(file);
        });

        return button;
    }
}
