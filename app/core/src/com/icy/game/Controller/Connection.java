package com.icy.game.Controller;

import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.icy.game.Views.CreateScreen;
import com.icy.game.Views.LobbyScreen;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class Connection {

    private Socket socket;
    private int playerId;
    private String playerTwoUsername, roomHost, room;
    private Vector2 opponentPos, opponentVel;

    public Connection(IcyGame game, String address) {
        opponentPos = new Vector2();
        opponentVel = new Vector2();
        try {
            socket = IO.socket(address);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to server");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connection lost");
                }
            }).on("createRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        playerId = Integer.parseInt(data.getString("pid"));
                        roomHost = data.getString("host");
                        room = data.getString("room");
                        System.out.println("Lobby created! Your ID: " + playerId + "\tRoom name: " + data.getString("room"));
                        // game.setScreen(new LobbyScreen(game, getPlayerId(), getRoomHost(), null, getRoomName()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("opponentJoined", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String res = data.getString("data");
                        System.out.println(res + " joined the lobby!");
                        playerTwoUsername = res;
                        LobbyScreen.addPlayerTwo(playerTwoUsername);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("joinRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        playerId = Integer.parseInt(data.getString("pid"));
                        roomHost = data.getString("host");
                        room = data.getString("room");
                        playerTwoUsername = data.getString("username");
                        System.out.println("Lobby joined! Your ID: " + playerId + "\tRoom name: " + room + "\tHost: " + roomHost);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("posRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        opponentPos.x = (float) data.getDouble("posX");
                        opponentPos.y = (float) data.getDouble("posY");
                        opponentVel.x = (float) data.getDouble("velX");
                        opponentVel.y = (float) data.getDouble("velY");
                        System.out.println(data.getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket.connect();
        } catch (Exception e) {
            System.out.println("Failed to connect. Error: " + e);
        }
    }

    public void createLobby(String username, String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("username", username);
        game.put("room", roomName);
        game.put("level", "none");
        game.put("powerups", "none");
        System.out.println("Sending create request");
        socket.emit("create", game);
    }

    public void joinLobby(final String username, final String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("username", username);
        game.put("room", roomName);
        socket.emit("join", game);
    }

    public void leaveLobby(final String username, final String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("username", username);
        game.put("room", roomName);
        socket.emit("leave", game);
    }

    public void sendPosition(final String roomName, final int playerId, final Vector2 pos, final Vector2 vel) throws JSONException{
        JSONObject player = new JSONObject();
        player.put("room", roomName);
        player.put("id", playerId);
        player.put("posX", (double) pos.x);
        player.put("posY", (double) pos.y);
        player.put("velX", (double) vel.x);
        player.put("velY", (double) vel.y);
        socket.emit("pos", player);
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerTwoUsername() {
        return playerTwoUsername;
    }

    public String getRoomHost() {
        return roomHost;
    }

    public String getRoomName() {
        return room;
    }

    public Vector2 getOpponentPos() {
        return opponentPos;
    }

    public Vector2 getOpponentVel() {
        return opponentVel;
    }
}
