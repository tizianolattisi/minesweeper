package com.lattisi.minesweeperqt;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMainWindow;

/**
 * User: tiziano
 * Date: 13/11/13
 * Time: 19:19
 */
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
