from flask import Flask, request
from flask_socketio import SocketIO, join_room, leave_room, emit, send
from Player import Player
from Game import Game

app = Flask(__name__)
app.config['SECRET_KEY'] = '321sfsdf23'
app.config['DEBUG'] = True
socketio = SocketIO(app)

games = {}


@socketio.on('connect', namespace='/')
def connect_success():
    emit('connectionResponse', {'data': 'Connected to server'})


@socketio.on('create')
def create_lobby(data):
    username = data['username']
    room = data['room']
    level = data['level']
    power_ups = data['powerups']
    join_room(room)
    game = Game(room, username, level, power_ups)
    games[room] = game
    print("Room created on server:", username, room, level, power_ups)
    emit('createRes', {'pid': 0, 'host': games[room].getHost(), 'room': room}, room=room)


@socketio.on('join')
def on_join(data):
    username = data['username']
    room = data['room']
    join_room(room)
    games[room].join(Player(username))
    send(username + ' has entered the room.', room=room)
    print("Player joined:", username, room)
    emit('joinRes', {'pid': 1, 'room': room, 'host': games[room].getHost(), 'username': username}, room=room)
    emit('opponentJoined', {'data': username}, room=room)
    

@socketio.on('leave')
def on_leave(data):
    username = data['username']
    room = data['room']
    leave_room(room)
    send(username + ' has left the room.', room=room)
    print("Player left:", username, room)


@socketio.on('test')
def test(data):
    print('\nTest message received: ', data, "\n SID: ", request.sid)


@socketio.on('pos')
def pos(data):
    games[data['room']].update(data['id'], [data['posX'], data['posY']], [data['velX'], data['velY']])
    print(data['id'])
    if (data['id' == 0]:
        emit('posRes1', {'id': data['id'], 'posX': data['posX'], 'posY': data['posY'], 'velX': data['velX'], 'velY': data['velY']})
    else:
        emit('posRes0', {'id': data['id'], 'posX': data['posX'], 'posY': data['posY'], 'velX': data['velX'], 'velY': data['velY']})

if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", port=7676)
