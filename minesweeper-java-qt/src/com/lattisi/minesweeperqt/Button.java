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
