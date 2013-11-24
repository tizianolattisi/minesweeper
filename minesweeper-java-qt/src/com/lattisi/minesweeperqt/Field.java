/*
 * Copyright (C) 2013 Tiziano Lattisi (http://www.lattisi.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.lattisi.minesweeperqt;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;

import java.util.HashMap;
import java.util.Map;

public class Field extends QMainWindow {

    private QGridLayout grid;
    private Map<Button, Integer[]> buttons = new HashMap<Button, Integer[]>();
    private final Game game;
    private QLCDNumber numOfBombs;
    private QLCDNumber numOfFlags;
    private QToolButton smileButton;

    public Field() {
        super();
        initLayout();
        setWindowTitle("Minesweeper");
        game = new Game();
        game.initGame();
        numOfBombs.display(game.numOfBombs());
        //numOfFlags.display(game.numOfFlags());
    }

    private void initLayout() {
        QWidget widget = new QWidget();
        this.setCentralWidget(widget);
        QVBoxLayout vLayout = new QVBoxLayout();
        QHBoxLayout hLayout = new QHBoxLayout();
        smileButton = new QToolButton();
        smileButton.setIcon(new QIcon("classpath:com/lattisi/minesweeperqt/images/smile.png"));
        smileButton.setFixedSize(new QSize(52, 52));
        smileButton.setIconSize(new QSize(48, 48));
        numOfBombs = new QLCDNumber();
        numOfFlags = new QLCDNumber();
        hLayout.addWidget(numOfBombs);
        hLayout.addWidget(smileButton);
        hLayout.addWidget(numOfFlags);
        vLayout.addLayout(hLayout);
        grid = new QGridLayout();
        vLayout.addLayout(grid);
        grid.setSpacing(0);
        widget.setLayout(vLayout);
        for( Integer i=0; i< Game.DIM; i++ ){
            for( Integer j=0; j< Game.DIM; j++ ){
                Button button = new Button();
                button.clicked.connect(this, "buttonClicked()");
                button.rightClicked.connect(this, "rightButtonClicked()");
                Integer[] cords = {i, j};
                buttons.put(button, cords);
                grid.addWidget(button, i, j);
            }
        }
    }

    private void buttonClicked(){
        QToolButton button = (QToolButton) signalSender();
        Integer[] coords = this.buttons.get(button);
        if( game.thereIsABomb(coords[0], coords[1]) ){
            QMessageBox.critical(this, "BOOOM!", "BOOOM!");
        } else {
            removeButton(button);
        }
    }

    private void rightButtonClicked(){
        QToolButton button = (QToolButton) signalSender();
        Integer[] coords = this.buttons.get(button);
        Integer i = coords[0];
        Integer j = coords[1];
        if( !this.game.isFlagged(i, j)){
            button.setIcon(new QIcon(new QPixmap("classpath:com/lattisi/minesweeperqt/images/flag.png")));
            this.game.setFlag(i, j);
        } else {
            button.setIcon(null);
            this.game.unSetFlag(i, j);
        }
        numOfBombs.display(game.numOfBombs() - game.numOfFlags());

    }

    private void removeButton(Integer i, Integer j){
        QLayoutItemInterface qLayoutItemInterface = grid.itemAtPosition(i, j);
        QToolButton button = (QToolButton) qLayoutItemInterface.widget();
        removeButton(button);
    }

    private void removeButton(QToolButton button) {
        Integer[] coords = this.buttons.get(button);
        game.dig(coords[0], coords[1]);
        button.close();
        QLabel label = new QLabel();
        String style = "background-color: white;font-weight:bold;";
        label.setAlignment(Qt.AlignmentFlag.AlignCenter);
        label.setFixedSize(new QSize(36, 36));
        Integer nOfBombs = game.howManyBombs(coords[0], coords[1]);
        if (nOfBombs>0){
            label.setText(game.howManyBombs(coords[0], coords[1]).toString());
            switch (nOfBombs){
                case 1:
                    style += "color: blue;";
                    break;
                case 2:
                    style += "color: green;";
                    break;
                case 3:
                    style += "color: red;";
                    break;
                case 4:
                    style += "color: navy;";
                    break;
                case 5:
                    style += "color: maroon;";
                    break;
                case 6:
                    style += "color: aqua;";
                    break;
                case 7:
                    style += "color: gray;";
                    break;
                case 8:
                    style += "color: black;";
                    break;
            }
        }
        label.setStyleSheet(style);
        grid.addWidget(label, coords[0], coords[1]);
        if (nOfBombs==0){
            for( Integer[] newCoords: game.neighbors(coords[0], coords[1]) ){
                removeButton(newCoords[0], newCoords[1]);
            }
        }
    }

}
