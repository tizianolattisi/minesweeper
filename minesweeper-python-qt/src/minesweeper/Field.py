from PyQt4 import QtGui, QtCore
from minesweeper.Button import Button
from minesweeper.Game import Game


class Field(QtGui.QMainWindow):

    def __init__(self):
        super(Field, self).__init__()
        self.buttons = {}
        self.initLayout()
        self.setWindowTitle("Minesweeper")
        self.game = Game()
        #game.initGame()
        #self.numOfBombs.display(self.game.numOfBombs())

    def initLayout(self):
        widget = QtGui.QWidget()
        self.setCentralWidget(widget)
        vLayout = QtGui.QVBoxLayout()
        hLayout = QtGui.QHBoxLayout()
        self.smileButton = QtGui.QToolButton()
        self.smileButton.setIcon(QtGui.QIcon("minesweeper/images/smile.png"))
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
        pass

    def rightButtonClicked(self):
        pass