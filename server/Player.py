class Player:

    def __init__(self, username, sid):
        self.username = username
        self.sid = sid
        self.pos = [0.0, 0.0]
        self.vel = [0.0, 0.0]

    def update(self, pos, vel):
        self.pos = pos
        self.vel = vel

    def getUsername(self):
        return self.username
