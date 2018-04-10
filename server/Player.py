class Player:

    def __init__(self, username):
        self.username = username
        self.pos = [0.0, 0.0]
        self.vel = [0.0, 0.0]

    def update(self, pos, vel):
        self.pos = pos
        self.vel = vel
        print("Position updated", pos, vel)

    def getUsername(self):
        return self.username