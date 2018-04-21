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

import static com.icy.game.IcyGame.cam;

public class Connection {
    private static final Connection INSTANCE = new Connection(IcyGame.URL);
    private Socket socket;
    private int playerId = -1;
    private String playerTwoUsername, roomHost, room;
    private Opponent opponent = Opponent.getInstance();
    private int removeTileId = -1;

    public static Connection getInstance() {
        return INSTANCE;
    }

    private Connection(String address) {
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
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            IcyGame.getInstance().setScreen(new LobbyScreen(getPlayerId(), getRoomHost(), null, getRoomName()));
                        }
                    });
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

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            LobbyScreen lobby = new LobbyScreen(getPlayerId(), getRoomHost(), getPlayerTwoUsername(), getRoomName());
                            lobby.joinLobby(getPlayerId(), getPlayerTwoUsername());
                            IcyGame.getInstance().setScreen(lobby);
                        }
                    });
                }
            }).on("posRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        opponent.setPosition(new Vector2((float) data.getDouble("posX"),(float) data.getDouble("posY")));
                        opponent.setVelocity(new Vector2((float) data.getDouble("velX"),(float) data.getDouble("velY")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("rematchRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Rematch");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        if(data.getBoolean("rematch")){
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    IcyGame.getInstance().setScreen(new LobbyScreen(Player.getInstance().getPlayerId(),
                                            Connection.getInstance().getRoomHost(),
                                            Opponent.getInstance().getUsername(),
                                            Connection.getInstance().getRoomName()
                                    ));
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("playerLeftRes", new Emitter.Listener() {
                String roomName;
                @Override
                public void call(Object... args) {
                    System.out.println("Player left. Returning to menu");
                    JSONObject data = (JSONObject) args[0];
                    try {
                        roomName = data.getString("room");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject room = new JSONObject();
                            try {
                                room.put("room", roomName);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            socket.emit("gameOver", room);
                            IcyGame.getInstance().setScreen(new MenuScreen());
                        }
                    });
                }
            }).on("powerupPickupRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        removeTileId = data.getInt("tileId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on("deathStatusRes", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        cam.position.y = cam.viewportHeight/2;
                        if(data.getBoolean("opponentDead")){
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    IcyGame.getInstance().setScreen(new EndScreen(Player.getInstance().getUsername()));
                                }
                            });
                        }
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

    public void leaveLobby(final int playerId, final String username, final String roomName) throws JSONException {
        JSONObject game = new JSONObject();
        game.put("id", playerId);
        game.put("username", username);
        game.put("room", roomName);
        reset();
        socket.emit("leave", game);
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

    public void sendPosition(final String roomName, final int playerId, final Vector2 pos, final Vector2 vel) throws JSONException{
        System.out.println("Player ID: "+playerId);
        JSONObject player = new JSONObject();
        player.put("room", roomName);
        player.put("id", playerId);
        player.put("posX", (double) pos.x);
        player.put("posY", (double) pos.y);
        player.put("velX", (double) vel.x);
        player.put("velY", (double) vel.y);
        socket.emit("pos", player);
    }

    public void sendPowerupPickup(final String roomName, final int playerId, final int tileId) throws JSONException{
        JSONObject tile = new JSONObject();
        tile.put("room", roomName);
        tile.put("id", playerId);
        tile.put("tileId",tileId);
        socket.emit("powerupPickup", tile);
    }
    public void sendDeathStatus(final String roomName) throws JSONException{
        JSONObject status = new JSONObject();
        status.put("room", roomName);
        socket.emit("deathStatus",status);
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerTwoUsername() {
        return playerTwoUsername;
    }

    public void reset() {
        this.playerId = -1;
        this.playerTwoUsername = null;
        this.roomHost = null;
        this.room = null;
    }

    public String getRoomHost() {
        return roomHost;
    }

    public String getRoomName() {
        return room;
    }
}