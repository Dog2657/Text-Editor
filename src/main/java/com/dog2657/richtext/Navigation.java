package com.dog2657.richtext;

import com.dog2657.richtext.components.NavigationButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Navigation extends HBox {
    public Navigation(Stage primaryStage){
        this.setStyle("-fx-background-color: rgb(66, 59, 84); -fx-padding: 8px;");
        this.setSpacing(8);
        this.getChildren().addAll(fileOpenButton(primaryStage), fileSaveButton(primaryStage));
    }

    private Button fileOpenButton(Stage stage){
        NavigationButton button = new NavigationButton("Open");

        button.setOnAction(event -> {
            FileChooser chooser = new FileChooser();

            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showOpenDialog(stage);

            Controller.openFile(file.getPath());
        });

        return button;
    }

    private Button fileSaveButton(Stage stage){
        NavigationButton button = new NavigationButton("Save");

        button.setOnAction(event -> {
            if(Model.getInstance().getFileLocation() == null){
                DirectoryChooser chooser = new DirectoryChooser();
                File file = chooser.showDialog(stage);

                Model.getInstance().setFileLocation(file + "/untitled.txt");
            }

            Controller.saveFile(Model.getInstance().getFileLocation());
            System.out.println("file save");
        });

        return button;
    }
}
