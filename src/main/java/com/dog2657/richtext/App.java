package com.dog2657.richtext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class App extends Application {
    Viewer viewer;
    Navigation navigation;

    @Override
    public void start(Stage stage){
        viewer = new Viewer(1350, 700);
        navigation = new Navigation(stage);

        BorderPane border = new BorderPane();
        border.setCenter(viewer);
        border.setTop(navigation);

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case LEFT -> viewer.moveCursorLeft();
                case RIGHT -> viewer.moveCursorRight();
            }
        });

        /*FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(stage);*/


        //Stops window being resized
        stage.setResizable(false);

        stage.setTitle("Rich text viewer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}