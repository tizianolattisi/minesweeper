package com.lattisi.minesweeperqt;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QToolButton;

public class Button extends QToolButton {

    public final Signal0 rightClicked = new Signal0();

    public Button() {
        super();
        installEventFilter(this);
        setFixedSize(new QSize(36, 36));
        setIconSize(new QSize(32, 32));
        setSizePolicy(QSizePolicy.Policy.Fixed, QSizePolicy.Policy.Fixed);
    }

    @Override
    public boolean eventFilter(QObject object, QEvent event) {
        if( event.type() == QEvent.Type.MouseButtonPress ){
            if( ((QMouseEvent) event).button() == Qt.MouseButton.RightButton ){
                this.rightClicked.emit();
                return true;
            }
        }
        return super.eventFilter(object, event);

    }
}
