package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.Selection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {
    Viewer viewer;
    Navigation navigation;

    boolean isHeld = false;

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