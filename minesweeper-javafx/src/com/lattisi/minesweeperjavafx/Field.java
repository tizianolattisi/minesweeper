package com.lattisi.minesweeperjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * User: tiziano
 * Date: 13/12/13
 * Time: 15:23
 */
public class Field {

    private static Game game;
    private static Map<Button, Integer[]> buttons = new HashMap<Button, Integer[]>();
    private static GridPane grid;

    public static Scene sceneBuilder(){

        grid = fieldBuilder();
        Scene scene = SceneBuilder.create()
                .width(32*Game.DIM)
                .height(32*Game.DIM)
                .root(grid)
                .build();

        game = new com.lattisi.minesweeperjavafx.Game();
        game.initGame();

        return scene;
    }

    private static GridPane fieldBuilder(){
        GridPane grid = GridPaneBuilder.create().build();
        for( int i=0; i<Game.DIM; i++ ){
            for( int j=0; j<Game.DIM; j++ ){
                Button button = ButtonBuilder.create()
                        .prefHeight(32)
                        .prefWidth(32)
                        .onAction(handlerPushButton)
                        .build();
                button.setId(i+","+j);
                grid.add(button, i, j);
                Integer[] cords = {i, j};
                buttons.put(button, cords);
            }
        }
        return grid;
    }

    private static void removeButton(Button button){
        Integer[] coords = buttons.get(button);
        grid.getChildren().remove(button);
        Label label = LabelBuilder.create()
                .prefWidth(32)
                .prefHeight(32)
                .build();
        Integer nOfBombs = game.howManyBombs(coords[0], coords[1]);
        label.setText(nOfBombs.toString());
        grid.add(label, coords[0], coords[1]);
        if (nOfBombs==0){
            for( Integer[] newCoords: game.neighbors(coords[0], coords[1]) ){
                //removeButton(newCoords[0], newCoords[1]);
            }
        }
    }

    private static EventHandler<ActionEvent> handlerPushButton = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Button button = (Button)e.getSource();
            Integer i = GridPane.getRowIndex(button);
            Integer j = GridPane.getColumnIndex(button);
            Integer[] coords = buttons.get(button);
            if( game.thereIsABomb(coords[0], coords[1]) ){
                System.out.println("BOOM!");
            } else {
                removeButton(button);
            }


        }
    };

}
