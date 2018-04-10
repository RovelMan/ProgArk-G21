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

    def update(self, player, pos, vel):
        self.players[player].update(pos, vel)

    def getHost(self):
        return self.players[0].getUsername()
