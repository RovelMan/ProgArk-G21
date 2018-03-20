class Game:

    def __init__(self, name, playerOne, level, powerUps):
        self.name = name
        self.players = [Player(playerOne)]
        self.level = level
        self.powerUps = powerUps

    def join(self, player):
        self.players.append(player)

    def update(self, player, pos):
        players[player].update(pos)
        p = 1 - player
        emit('pos', pos)
