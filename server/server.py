from flask import Flask
from flask_socketio import SocketIO, join_room, leave_room, emit

app = Flask(__name__)
app.config['SECRET_KEY'] = '321sfsdf23'
app.config['DEBUG'] = True
socketio = SocketIO(app)


@socketio.on('connect', namespace='/')
def connect_success():
    emit('connectionResponse', {'data': 'Connected'})
    print('heyho')


@socketio.on('join')
def on_join(data):
    username = data['username']
    room = data['room']
    join_room(room)
    send(username + ' has entered the room.', room=room)


@socketio.on('leave')
def on_leave(data):
    username = data['username']
    room = data['room']
    leave_room(room)
    send(username + ' has left the room.', room=room)


@socketio.on('update')
def on_leave(data):
    print('yo')


if __name__ == '__main__':
    socketio.run(app, host="0.0.0.0", port="7676")
