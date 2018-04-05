class Player:

    def __init__(self, username):
        self.username = username
        self.pos = [0.0, 0.0]

    def update(self, pos):
        self.pos = pos

    def getUsername(self):
        return self.username