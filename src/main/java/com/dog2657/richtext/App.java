package com.dog2657.richtext;

import com.dog2657.richtext.DataClasses.Selection;
import com.dog2657.richtext.exceptions.SelectionEmptyException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class App extends Application {
    Viewer viewer;
    Navigation navigation;


    boolean isDragging = false;

    @Override
    public void start(Stage stage){
        //MVC architecture
        viewer = new Viewer(1350, 700);
        Model.getInstance().setViewer(viewer);

        navigation = new Navigation(stage);

        BorderPane border = new BorderPane();
        border.setCenter(viewer);
        border.setTop(navigation);


        viewer.setOnMousePressed((MouseEvent event) -> {
            int position = Model.getInstance().getCursor().translateXYLocation(event.getX(), event.getY(), true);

            Model.getInstance().getCursor().setSelection(new Selection(position));
            viewer.update();
        });

        viewer.setOnMouseReleased((MouseEvent event) -> {
            try {
                Selection selection = Model.getInstance().getCursor().getSelection();

                System.out.println(selection.getContent());

            } catch (SelectionEmptyException e) {
                throw new RuntimeException(e);
            }
            //Model.getInstance().getCursor().setSelection(null);
        });

        viewer.setOnMouseClicked((MouseEvent event) -> {
            //System.out.println("clicked");
        });

        viewer.setOnMouseDragged((MouseEvent event) -> {
            int position = Model.getInstance().getCursor().translateXYLocation(event.getX(), event.getY(), true);

            try{
                Model.getInstance().getCursor().getSelection().setEnd(position);
            }catch(SelectionEmptyException error) {
                System.out.println("Attempted to update end of selection without an active selection");
                Model.getInstance().getCursor().setSelection(new Selection(position));
            }

            viewer.update();
        });

        /*

        viewer.setOnMouseClicked((MouseEvent event) -> {
            CompletableFuture.delayedExecutor(5, TimeUnit.MICROSECONDS).execute(() -> {
                if(isDragging){
                    System.out.println("Draging");
                }
            });

        });*/


        /*viewer.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Circle 1 drag detected");

            Dragboard db = viewer.startDragAndDrop(TransferMode.COPY_OR_MOVE);

            // set drag and drop data
            ClipboardContent content = new ClipboardContent();
            content.putString("Circle source text");
            db.setContent(content);
        });

        viewer.setOnMouseDragged((MouseEvent event) -> {
            Controller.handleSelect(event);
            event.setDragDetect(true);
        });

        viewer.setOnDragOver((DragEvent event) -> {
                if (event.getGestureSource() != viewer && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
        });

        viewer.setOnDragDropped((DragEvent event) -> {
            event.consume();
        });



        viewer.setOnMouseClicked((MouseEvent event) -> {


            System.out.println("Click");
            //Controller.moveCursor(event.getX(), event.getY());
        });*/

        Scene scene = new Scene(border, 1350, 700);
        scene.setOnKeyPressed(e -> {
            if(e.isControlDown()){
                switch (e.getCode()){
                    case C -> {


                        try{
                            System.out.println(Model.getInstance().getCursor().getSelection());
                            return;
                            /*String text = Model.getInstance().getCursor().getSelection().getContent();

                            return;*/
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