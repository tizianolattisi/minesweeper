from PyQt4 import QtGui, QtCore

class Button(QtGui.QToolButton):

    rightClicked = QtCore.pyqtSignal()

    def __init__(self):
        super(Button, self).__init__()
        self.installEventFilter(self)
        self.setFixedSize(36, 36)
        self.setIconSize(QtCore.QSize(32, 32))
        self.setSizePolicy(QtGui.QSizePolicy.Fixed, QtGui.QSizePolicy.Fixed)

    def eventFilter(self, obj, event):
        if event.type() == QtCore.QEvent.MouseButtonPress:
            if event.button() == QtCore.Qt.RightButton:
                self.rightClicked.emit()
                return True
        return super(Button, self).eventFilter(obj, event)
