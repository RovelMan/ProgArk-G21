package com.icy.game.Controller;

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
    private String playerTwoUsername;
    private String roomHost;
    private String room;

    public Connection(IcyGame game, String address) {
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

}
