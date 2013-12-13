package com.lattisi.minesweeperjavafx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * User: tiziano
 * Date: 13/12/13
 * Time: 15:17
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Minesweeper JavaFX");
        primaryStage.setScene(Field.sceneBuilder());
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
