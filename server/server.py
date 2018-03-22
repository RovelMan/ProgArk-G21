from flask import Flask, request
from flask_socketio import SocketIO, join_room, leave_room, emit, send
from player import Player
from game import Game

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
    emit('pid', {'data': 0})


@socketio.on('join')
def on_join(data):
    username = data['username']
    room = data['room']
    join_room(room)
    games[room].join(Player(username))
    send(username + ' has entered the room.', room=room)
    emit('pid', 1)


@socketio.on('leave')
def on_leave(data):
    username = data['username']
    room = data['room']
    leave_room(room)
    send(username + ' has left the room.', room=room)


@socketio.on('test')
def test(data):
    print('\nTest message received: ', data, "\n SID: ", request.sid)


@socketio.on('pos')
def pos(data):
    games[data['room']].update(data['id'], data['pos'])


if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", port=7676)
