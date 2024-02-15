package com.dog2657.richtext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {
    Viewer viewer;

    @Override
    public void start(Stage stage){
        viewer = new Viewer(1350, 700);

        BorderPane border = new BorderPane();
        border.setCenter(viewer);

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case LEFT -> viewer.moveCursorLeft();
                case RIGHT -> viewer.moveCursorRight();
            }
        });

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