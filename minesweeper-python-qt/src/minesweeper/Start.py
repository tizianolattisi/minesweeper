import sys
from PyQt4 import QtGui, QtCore
from minesweeper.Field import Field

def main(argv):
    app = QtGui.QApplication(argv)
    win = Field()
    win.show()

    app.exec()

if __name__ == '__main__':
    main(sys.argv)