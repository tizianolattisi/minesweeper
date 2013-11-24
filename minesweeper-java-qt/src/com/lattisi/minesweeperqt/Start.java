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

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMainWindow;

public class Start {

    public static void main(String[] args){

        QApplication app = new QApplication(args);

        /*
        QLabel label = new QLabel("hello");
        label.show();
        */


        //QMainWindow win = new QMainWindow();
        Field win = new Field();
        win.show();


        app.exec();


    }

}
