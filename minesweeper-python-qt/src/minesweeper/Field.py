from PyQt4 import QtGui, QtCore
from minesweeper.Button import Button
from minesweeper.Game import Game


class Field(QtGui.QMainWindow):

    COLORS = ("blue", "green", "red", "navy", "maroon", "aqua", "gray", "black")

    def __init__(self):
        super(Field, self).__init__()
        self.buttons = {}
        self.initLayout()
        self.setWindowTitle("Minesweeper")
        self.game = Game()
        self.game.initGame()
        self.numOfBombs.display(self.game.numOfBombs())

    def initLayout(self):
        widget = QtGui.QWidget()
        self.setCentralWidget(widget)
        vLayout = QtGui.QVBoxLayout()
        hLayout = QtGui.QHBoxLayout()
        self.smileButton = QtGui.QToolButton()
        self.smileButton.setIcon(QtGui.QIcon("./images/smile.png"))
        self.smileButton.setFixedSize(52, 52)
        self.smileButton.setIconSize(QtCore.QSize(48, 48))
        self.numOfBombs = QtGui.QLCDNumber()
        self.numOfFlags = QtGui.QLCDNumber()
        hLayout.addWidget(self.numOfBombs)
        hLayout.addWidget(self.smileButton)
        hLayout.addWidget(self.numOfFlags)
        vLayout.addLayout(hLayout)
        self.grid = QtGui.QGridLayout()
        vLayout.addLayout(self.grid)
        self.grid.setSpacing(0)
        widget.setLayout(vLayout)
        for i in range(Game.DIM):
            for j in range(Game.DIM):
                button = Button()
                button.clicked.connect(self.buttonClicked)
                button.rightClicked.connect(self.rightButtonClicked)
                self.buttons[button] = (i, j)
                self.grid.addWidget(button, i, j)

    def buttonClicked(self):
        button = self.sender()
        (i, j) = self.buttons[button]
        if( self.game.thereIsABomb(i, j) ):
            QtGui.QMessageBox.critical(self, "BOOM!", "BOOM!")
        else:
            self.removeButton(button)

    def rightButtonClicked(self):
        button = self.sender()
        (i, j) = self.buttons[button]
        if not self.game.isFlagged(i, j):
            button.setIcon(QtGui.QIcon("./images/flag.png"))
            self.game.setFlag(i, j)
        else:
            button.setIcon(QtGui.QIcon(None))
            self.game.unSetFlag(i, j)
        self.numOfBombs.display(self.game.numOfBombs() - self.game.numOfFlags())

    def removeButton(self, button):
        (i, j) = self.buttons[button]
        self.game.dig(i, j)
        button.close()
        label = QtGui.QLabel()
        style = "background-color: white;font-weight:bold;"
        label.setAlignment(QtCore.Qt.AlignCenter)
        label.setFixedSize(QtCore.QSize(36, 36))
        numOfBombs = self.game.howManyBombs(i, j)
        if numOfBombs>0:
            label.setText(str(numOfBombs))
            style += "color: " + self.COLORS[numOfBombs-1] + ";"
        label.setStyleSheet(style)
        self.grid.addWidget(label, i, j)
        if numOfBombs==0:
            for (x, y) in self.game.neighbors(i, j):
                qLayoutItemInterface = self.grid.itemAtPosition(x, y)
                button = qLayoutItemInterface.widget()
                self.removeButton(button)
