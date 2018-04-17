from flask_socketio import emit
from Player import Player


class Game:

    def __init__(self, name, level, powerUps):
        self.name = name
        self.players = []
        self.level = level
        self.powerUps = powerUps

    def join(self, player):
        self.players.append(player)

    def getHost(self):
        return self.players[0]
