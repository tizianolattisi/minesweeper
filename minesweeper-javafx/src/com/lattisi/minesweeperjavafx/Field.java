package com.lattisi.minesweeperjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.paint.Color;

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
    private static String[] colors = {"blue", "green", "red", "navy", "maroon", "aqua", "gray", "black"};

    public static Scene sceneBuilder(){

        grid = fieldBuilder();
        Scene scene = SceneBuilder.create()
                .width(32*Game.DIM)
                .height(32*Game.DIM)
                .root(grid)
                .build();

        game = new Game();
        game.initGame();

        return scene;
    }

    private static GridPane fieldBuilder(){
        GridPane grid = GridPaneBuilder.create().build();
        for( int i=0; i<Game.DIM; i++ ){
            for( int j=0; j<Game.DIM; j++ ){
                Button button = ButtonBuilder.create()
                        .prefHeight(36)
                        .prefWidth(36)
                        .onAction(handlerPushButton)
                        .build();
                button.setId(i+","+j);
                button.setStyle("-fx-padding: 2px;");
                button.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerRightPushButton);

                grid.add(button, i, j);
                Integer[] cords = {i, j};
                buttons.put(button, cords);
            }
        }
        return grid;
    }

    private static void removeButton(Integer[] coords){
        Integer x = coords[0];
        Integer y = coords[1];
        for( Button button: buttons.keySet() ){
            Integer[] newCoords = buttons.get(button);
            if( newCoords[0] == x && newCoords[1] == y ){
                removeButton(button);
            }
        }

    }

    private static void removeButton(Button button){
        Integer[] coords = buttons.get(button);
        game.dig(coords[0], coords[1]);
        grid.getChildren().remove(button);
        Label label = LabelBuilder.create()
                .prefWidth(36)
                .prefHeight(36)
                .build();
        Integer nOfBombs = game.howManyBombs(coords[0], coords[1]);
        label.setText(nOfBombs.toString());
        label.setAlignment(Pos.CENTER);
        if (nOfBombs==0){
            for( Integer[] newCoords: game.neighbors(coords[0], coords[1]) ){
                removeButton(newCoords);
            }
        } else {
            String color = colors[nOfBombs];
            label.setTextFill(Color.web(color));
            grid.add(label, coords[0], coords[1]);
        }
    }

    private static void rightButtonClicked(Button button){
        Integer[] coords = buttons.get(button);
        Integer i = coords[0];
        Integer j = coords[1];
        if( !game.isFlagged(i, j)){
            Image flag = new Image(Field.class.getResourceAsStream("images/flag.png"));
            ImageView imageView = new ImageView(flag);
            imageView.setFitHeight(24);
            imageView.setFitWidth(24);
            button.setGraphic(imageView);
            game.setFlag(i, j);
        } else {
            button.setGraphic(null);
            game.unSetFlag(i, j);
        }
        //numOfBombs.display(game.numOfBombs() - game.numOfFlags());

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

    private static EventHandler<MouseEvent> handlerRightPushButton = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                Object source = event.getSource();
                if( source instanceof Button ){
                    rightButtonClicked((Button) source);
                }
            }
        }
    };

}
