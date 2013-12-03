
import sys
import random
from PyQt4 import QtGui, QtCore

DIM = 12

class MinesField(object):

    def __init__(self):
        self.bombs = {}
        self.flags = {}
        self.digged = {}
        self.place_bombs()

    def place_bombs(self):
        for i in range(DIM):
            for j in range(DIM):
                self.flags[(i, j)] = False
                self.digged[(i, j)] = False
                r = random.random()
                if r > 0.9:
                    self.bombs[(i, j)] = True
                else:
                    self.bombs[(i, j)] = False

    def is_bomb(self, i, j):
        return self.bombs[(i, j)]

    def how_many_bombs(self, i, j):
        c = 0
        for x in (i-1, i, i+1):
            for y in (j-1, j, j+1):
                if x in range(DIM) and y in range(DIM):
                    if self.is_bomb(x, y):
                        c += 1
        return c


class MinesWeeper(QtGui.QMainWindow):

    def __init__(self):
        super().__init__()
        self.pulsanti = {}
        self.coordinate = {}
        self.bandierina = QtGui.QCheckBox("bandierina")
        #self.numOfBombs = QtGui.QLCDNumber()
        #self.numOfDigged = QtGui.QLCDNumber()
        vlayout = QtGui.QVBoxLayout()
        vlayout.addWidget(self.bandierina)
        self.layout = QtGui.QGridLayout()
        self.layout.setSpacing(0)
        centralWidget = QtGui.QWidget(self)
        vlayout.addLayout(self.layout)
        centralWidget.setLayout(vlayout)
        self.setCentralWidget(centralWidget)
        self.init_layout()
        self.installEventFilter(self)
        self.mines_field = MinesField()


    def init_layout(self):
        for i in range(DIM):
            for j in range(DIM):
                button = QtGui.QToolButton()
                button.setObjectName("{0},{1}".format(i,j))
                self.layout.addWidget(button, i, j)
                self.pulsanti[(i, j)] = button
                self.coordinate[button] = (i, j)
                self.connect(button, QtCore.SIGNAL("clicked()"), self, QtCore.SLOT("press()"))

    def eventFilter(self, receiver, event):
        if event.type() == QtCore.QEvent.MouseButtonPress:
            if event.button() == QtCore.Qt.RightButton:
                print("right click")
                return True
        return super(MinesWeeper,self).eventFilter(receiver, event)


    @QtCore.pyqtSlot()
    def press(self, i=None, j=None):
        print(self.sender())
        bandierina = False #self.bandierina.isChecked()
        objectName = self.sender().objectName()
        if i is None and j is None:
            i, j = [int(x) for x in  objectName.split(",")]
        #button = self.pulsanti[(i, j)]
        button = self.sender()
        if bandierina:
            if not self.bandierine[(i, j)]:
                button.setIcon(QtGui.QIcon("bomb.png"))
                self.mines_field.flags[(i, j)] = True
            else:
                button.setIcon(QtGui.QIcon())
                self.mines_field.flags[(i, j)] = False
        else:
            button.close()
            self.mines_field.digged[(i, j)] = True # XXX
            item = QtGui.QLabel()
            item.setAlignment(QtCore.Qt.AlignCenter)
            item.setStyleSheet("background-color: white;") # XXX
            if self.mines_field.is_bomb(i, j) is True:
                item.setPixmap(QtGui.QPixmap("bomb.png"))
            else:
                c = self.mines_field.how_many_bombs(i, j)
                if c>0:
                    item.setText(str(c))
                else:
                    self.bonifica_zona(i, j)
            self.layout.addWidget(item, i, j)

    def bonifica_zona(self, i, j):
        for x in (i-1, i, i+1):
            for y in (j-1, j, j+1):
                if x in range(DIM) and y in range(DIM):
                    if self.mines_field.digged[(x, y)] is False:
                        self.press(x, y)


if __name__ == '__main__':

    app = QtGui.QApplication(sys.argv)

    win = MinesWeeper()
    win.show()

    sys.exit(app.exec_())