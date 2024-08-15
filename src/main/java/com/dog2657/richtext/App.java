package com.dog2657.richtext;

import com.dog2657.richtext.exceptions.SelectionEmptyException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.*;
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

        viewer.setOnMouseClicked((MouseEvent event) -> Controller.moveCursor(event.getX(), event.getY()));
        viewer.setOnMouseDragged((MouseEvent event) -> Controller.handleSelect(event));

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {
            if(e.isControlDown()){
                switch (e.getCode()){
                    case C -> {
                        try{
                            String text = Model.getInstance().getCursor().getSelection().getContent();

                            return;
                        }catch(SelectionEmptyException error){
                            System.out.println("Unable to copy selection due to it being empty");
                        }
                    }
                }
            }
            switch (e.getCode()){
                case LEFT -> Controller.moveCursorLeft(e.isShiftDown());
                case RIGHT -> Controller.moveCursorRight(e.isShiftDown());
                case UP -> Controller.moveCursorUp();
                case DOWN -> Controller.moveCursorDown();

                case BACK_SPACE -> Controller.delete(false);
                case DELETE -> Controller.delete(true);

                case ENTER -> Controller.makeNewLine();

                default -> {
                    if(e.getText().length() == 1)
                        Controller.addCharacter( e.getText() );
                }
            }
        });


        //TODO: Make window resizeable
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