package com.dog2657.richtext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    Viewer viewer;

    @Override
    public void start(Stage stage){
        viewer = new Viewer(1350, 700);

        BorderPane border = new BorderPane();
        border.setCenter(viewer);

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {
            String result = e.getText();
            System.out.println(e.getCode().isLetterKey());

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