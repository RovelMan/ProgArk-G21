package com.icy.game.Controller;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by havard on 13.03.18.
 */

public class Connection {

    private Socket socket;

    public Connection(String address) {
        try {
            socket = IO.socket(address);

            System.out.println("Connected to: " + socket);

            socket.on("connectionResponse", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[1];
                    try {
                        String text = data.getString("data");
                        System.out.println("JSON: " + text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            socket.connect();
        } catch (Exception e) {
            System.out.println("Failed to connect..Error: " + e);
        }
    }

    public void createLobby(String username, String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("username", username);
        game.put("room", roomName);
        game.put("level", "no level");
        game.put("powerups", "no powerups");
        socket.emit("create", game);
        socket.on("create", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[1];
                try {
                    String text = data.getString("data");
                    System.out.println("JSON: " + text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Socket getSocket() {
        return socket;
    }

}
