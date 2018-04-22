from flask import Flask, request
from flask_socketio import SocketIO, join_room, leave_room, emit, send
from Player import Player
from Game import Game
import time

app = Flask(__name__)
app.config['SECRET_KEY'] = '321sfsdf23'
app.config['DEBUG'] = True
socketio = SocketIO(app)

games = {}
players = {}


@socketio.on('connect')
def connect():
    sid = request.sid
    players[sid] = Player(sid)
    print("Device connected - SID:", sid)


@socketio.on('disconnect')
def disconnect():
    sid = request.sid
    players.pop(sid)
    print("Device disconnected - SID:", sid)


@socketio.on('create')
def create_lobby(data):
    player = players[request.sid]
    username = data['username']
    room = data['room']
    level = data['level']
    power_ups = data['powerups']

    if games.get(room):
        print("Room allready exists:", room)
        emit('createRes', {'pid': -1, 'host': None, 'room': None}, room=request.sid)
    else:
        join_room(room)
        game = Game(room, level, power_ups)
        player.join(username, game)
        games[room] = game
        print("Room created on server:", username, room, level, power_ups, request.sid)
        emit('createRes', {'pid': 0, 'host': games[room].getHost().username, 'room': room}, room=request.sid)


@socketio.on('join')
def on_join(data):
    player = players[request.sid]
    username = data['username']
    room = data['room']

    player.join(username, games[room])

    join_room(room)
    print("Player joined:", username, room)
    host = games[data['room']].getHost().sid
    emit('joinRes', {'pid': 1, 'room': room, 'host': games[room].getHost().username, 'username': username}, room=request.sid)
    emit('opponentJoined', {'data': username}, room=host)


@socketio.on('rematch')
def on_rematch(data):
    opponent = games[data['room']].players[1 - data['id']].sid
    emit('rematchRes', {'rematch': True}, room=opponent)


@socketio.on('leave')
def on_leave(data):
    username = data['username']
    room = data['room']
    leave_room(room)
    send(username + ' has left the room.', room=room)
    print("Player left:", username, room)
    opponent = games[data['room']].players[1 - data['id']].sid
    emit('playerLeftRes', {'room': data['room']}, room=opponent)


@socketio.on('gameOver')
def test(data):
    games.pop(data['room'])


@socketio.on('test')
def tests(data):
    print('\nTest message received: ', data, '\n SID: ', request.sid)


@socketio.on('pos')
def pos(data):
    try:
        for player in games[data['room']].players:
            if not player.sid == request.sid:
                opponent = player.sid
                break
        emit('posRes', {'posX': data['posX'], 'posY': data['posY'], 'velX': data['velX'], 'velY': data['velY']}, room=opponent)
    except:
        pass  # Other player has allready left


@socketio.on('powerupPickup')
def powerupPickup(data):
    opponent = games[data['room']].players[1 - data['id']].sid
    emit('powerupPickupRes', {'tileId': data['tileId']}, room=opponent)


@socketio.on('deathStatus')
def deathStatus(data):
    for player in games[data['room']].players:
        if not player.sid == request.sid:
            opponent = player.sid
    emit('deathStatusRes', {'opponentDead': True}, room=opponent)


if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", port=7676)
