from flask_socketio import emit
from Player import Player


class Game:

    def __init__(self, name, playerOne, level, powerUps):
        self.name = name
        self.players = [Player(playerOne)]
        self.level = level
        self.powerUps = powerUps

    def join(self, player):
        self.players.append(player)

    def update(self, player, pos):
        self.players[player].update(pos)
        pos = 1 - player
        emit('pos', pos)

    def getHost(self):
        return self.players[0].getUsername()
