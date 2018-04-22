package com.icy.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.icy.game.Models.Opponent;
import com.icy.game.Models.Player;
import com.icy.game.Views.EndScreen;
import com.icy.game.Views.LobbyScreen;
import com.icy.game.Views.MenuScreen;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class Connection {
    private static final Connection INSTANCE = new Connection(IcyGame.URL);
    private Socket socket;
    private String room;
    private Opponent opponent = Opponent.getInstance();
    private int removeTileId = -1;

    public static Connection getInstance() {
        return INSTANCE;
    }

    private Connection(String address) {
        try {
            socket = IO.socket(address);
            socket.on(Socket.EVENT_CONNECT, args ->
                System.out.println("Connected to server")
            ).on(Socket.EVENT_DISCONNECT, args ->
                    System.out.println("Connection lost, you are now disconnected")
            ).on("createRes", args -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    Player.getInstance().setPlayerId(Integer.parseInt(data.getString("pid")));
                    room = data.getString("room");
                    System.out.println("Lobby created! Your ID: " + Player.getInstance().getPlayerId() + "\tRoom name: " + data.getString("room"));
                    // game.setScreen(new LobbyScreen(game, getPlayerId(), getRoomHost(), null, getRoomName()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gdx.app.postRunnable(() -> IcyGame.getInstance().setScreen(LobbyScreen.getInstance()));
            }).on("opponentJoined", args -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    String res = data.getString("data");
                    System.out.println(res + " joined the lobby!");
                    opponent.setUsername(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).on("joinRes", args -> {
                Gdx.app.postRunnable(() -> {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if (data.getInt("pid") == -1) {
                            IcyGame.getInstance().setScreen(MenuScreen.getInstance());
                        } else {
                            room = data.getString("room");
                            opponent.setUsername(data.getString("host"));
                            System.out.println("Lobby joined! Your ID: " + Player.getInstance().getPlayerId() + "\tRoom name: " + room + "\tHost: " + data.getString("host"));
                            IcyGame.getInstance().setScreen(LobbyScreen.getInstance());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }).on("posRes", args -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    Opponent.getInstance().setPosition(new Vector2((float) data.getDouble("posX"), (float) data.getDouble("posY")));
                    Opponent.getInstance().setVelocity(new Vector2((float) data.getDouble("velX"), (float) data.getDouble("velY")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).on("rematchRes", args -> {
                System.out.println("Rematch");
                JSONObject data = (JSONObject) args[0];
                try {
                    if (data.getBoolean("rematch")) {
                        Player.getInstance().resetProperties();
                        Opponent.getInstance().resetProperties();
                        Gdx.app.postRunnable(() -> IcyGame.getInstance().setScreen(LobbyScreen.getInstance()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).on("playerLeftRes", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    System.out.println("Player left. Returning to menu");

                    Gdx.app.postRunnable(() -> {
                        JSONObject room = new JSONObject();
                        try {
                            room.put("room", room);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        socket.emit("gameOver", room);
                        Player.getInstance().resetProperties();
                        Opponent.getInstance().resetProperties();
                        IcyGame.getInstance().setScreen(MenuScreen.getInstance());
                    });
                }
            }).on("powerupPickupRes", args -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    removeTileId = data.getInt("tileId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }).on("deathStatusRes", args -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    if (data.getBoolean("opponentDead")) {
                        Gdx.app.postRunnable(() -> Player.getInstance().resetProperties());
                        EndScreen.getInstance().setWinner(true);
                        Gdx.app.postRunnable(() -> IcyGame.getInstance().setScreen(EndScreen.getInstance()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            socket.connect();
        } catch (Exception e) {
            System.out.println("Failed to connect. Error: " + e);
        }
    }

    public int getRemoveTileId() {
        return removeTileId;
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

    public void rematch(final String roomName, final int playerId) throws JSONException {
        JSONObject rematch = new JSONObject();
        rematch.put("id", playerId);
        rematch.put("room", roomName);
        socket.emit("rematch", rematch);
    }

    public void gameOver(final String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("room", roomName);
        socket.emit("gameOver", game);
    }

    public void sendPosition(final Vector2 pos, final Vector2 vel) throws JSONException {
        JSONObject player = new JSONObject();
        player.put("room", room);
        player.put("id", Player.getInstance().getPlayerId());
        player.put("posX", (double) pos.x);
        player.put("posY", (double) pos.y);
        player.put("velX", (double) vel.x);
        player.put("velY", (double) vel.y);
        socket.emit("pos", player);
    }

    public void sendPowerupPickup(final int tileId) throws JSONException {
        JSONObject tile = new JSONObject();
        tile.put("room", room);
        tile.put("id", Player.getInstance().getPlayerId());
        tile.put("tileId", tileId);
        socket.emit("powerupPickup", tile);
    }

    public void sendDeathStatus(final String roomName) throws JSONException {
        JSONObject status = new JSONObject();
        status.put("room", roomName);
        socket.emit("deathStatus", status);
    }


    public void setRemoveTileId(int removeTileId) {
        this.removeTileId = removeTileId;
    }

    public String getRoomName() {
        return room;
    }
}