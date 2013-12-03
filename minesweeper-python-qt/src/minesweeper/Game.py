from random import random

class Game(object):

    DIM=9
    DENSITY=0.15

    def __init__(self):
        self.bombs = {}
        self.digged = {}
        self.flags = {}

    def initGame(self):
        for i in range(self.DIM):
            for j in range(self.DIM):
                r = random()
                self.digged[(i, j)] = False
                self.flags[(i, j)] = False
                if r < self.DENSITY:
                    self.bombs[(i, j)] = True
                else:
                    self.bombs[(i, j)] = False

    def thereIsABomb(self, i, j):
        return self.bombs[(i, j)]

    def numOfBombs(self):
        c = 0
        for i in range(self.DIM):
            for j in range(self.DIM):
                if self.thereIsABomb(i, j):
                    c += 1
        return c

    def numOfFlags(self):
        c = 0
        for i in range(self.DIM):
            for j in range(self.DIM):
                if self.isFlagged(i, j):
                    c += 1
        return c

    def isDigged(self, i, j):
        return self.digged[(i, j)]

    def howManyBombs(self, i, j):
        c = 0
        for x in (i-1,i, i+1):
            for y in (j-1, j, j+1):
                if x>=0 and x<self.DIM and y>=0 and y<self.DIM:
                    if self.thereIsABomb(x, y):
                        c += 1
        return c

    def dig(self, i, j):
        if self.digged[(i, j)]:
            return
        self.digged[(i, j)] = True

    def isFlagged(self, i, j):
        return self.flags[(i, j)]

    def setFlag(self, i, j):
        self.flags[(i, j)] = True

    def unSetFlag(self, i, j):
        self.flags[(i, j)] = False

    def neighbors(self, i, j):
        coordsList = []
        for x in (i-1,i, i+1):
            for y in (j-1, j, j+1):
                if x>=0 and x<self.DIM and y>=0 and y<self.DIM:
                    if (not (x==i and y==i)) and (not self.digged[(x, y)]):
                        coordsList.append((x, y))
        return coordsList