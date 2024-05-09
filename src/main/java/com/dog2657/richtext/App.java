package com.dog2657.richtext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {
    Viewer viewer;
    Navigation navigation;

    @Override
    public void start(Stage stage){
        //MVC architecture
        viewer = new Viewer(1350, 700);
        Model.getInstance().setViewer(viewer);




        navigation = new Navigation(stage);

        BorderPane border = new BorderPane();
        border.setCenter(viewer);
        border.setTop(navigation);

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {

            System.out.printf(String.valueOf(e.getCode()));

            switch (e.getCode()){
                case LEFT -> {
                    System.out.println("Left");
                    Controller.moveCursorLeft(1);
                }
                case RIGHT -> Controller.moveCursorRight(1);
                case SPACE -> Controller.addCharacter(' ');
                default -> {
                    Controller.addCharacter( e.getText() );
                }
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