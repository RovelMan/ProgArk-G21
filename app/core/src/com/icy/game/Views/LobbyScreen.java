package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.Controller.Connection;
import com.icy.game.IcyGame;
import com.icy.game.Models.Opponent;
import com.icy.game.Models.Player;


public class LobbyScreen implements Screen {

    private static int playerId;
    private static String[] players = {null, null};
    private static String room;
    private Texture background;
    private float savedDelta = -1;
    private float timeElapsed;

    private static Stage stage;

    public LobbyScreen(int newPlayerId, String host, String playerTwo, String roomName) {
        System.out.println("LOBBY: " + newPlayerId + " " + host + " " + playerTwo + " " + roomName + " " + players[0] + " " + players[1]);
        reset();
        playerId = newPlayerId;
        players[0] = host;
        Gdx.input.setInputProcessor(stage);
        if (playerTwo != null) {
            addPlayerTwo(playerTwo);
        }
        System.out.println(playerId + 1 + " PLAYERS IN ROOM " + players[0] + " " + players[1]);
        room = roomName;

        background = new Texture("Backgrounds/default_background.png");

        stage = new Stage();
        updateLobby(playerTwo);
    }

    public void joinLobby(int playerId, String player) {
        players[playerId] = player;
        System.out.println("Player joined " + playerId + " " + player);
    }

    private void updateLobby(String playerTwo) {
        System.out.println("UPDATED: " + playerTwo);

        BitmapFont font = IcyGame.font;

        Label lobbyTxt = new Label("Room name: " + room, new Label.LabelStyle(font, Color.WHITE));
        Label hostName = new Label("Host: " + players[0], new Label.LabelStyle(font, Color.WHITE));
        Label playerName;
        Label info;
        if (playerTwo == null) {
            playerName = new Label("You: " + players[0], new Label.LabelStyle(font, Color.WHITE));
            info = new Label("Waiting for opponent...", new Label.LabelStyle(font, Color.WHITE));
        } else if (!players[0].equals(playerTwo)) {
            playerName  = new Label("You: " + players[1], new Label.LabelStyle(font, Color.WHITE));
            info = new Label("Opponent: " + players[0], new Label.LabelStyle(font, Color.WHITE));
        } else {
            playerName = new Label("You: " + players[0], new Label.LabelStyle(font, Color.WHITE));
            info = new Label("Opponent: " + playerTwo, new Label.LabelStyle(font, Color.WHITE));
        }


        int width = Gdx.graphics.getWidth();

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(lobbyTxt).expandX().padBottom(10).width(width);
        table.row();
        table.add(hostName).expandX().padBottom(10).width(width);
        table.row();
        table.add(playerName).expandX().padBottom(10).width(width);
        table.row();
        table.add(info).expandX().padBottom(10).width(width);
        table.pack();
        stage = new Stage();
        stage.addActor(table);
    }

    public static void addPlayerTwo(String username) {
        players[1] = username;
    }

    private void reset() {
        playerId = -1;
        players[0] = null;
        players[1] = null;
        room = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;
        if (players[0] != null && players[1] != null) {
            if (savedDelta == -1) {
                savedDelta = timeElapsed;
            }
            System.out.println("Both players joined. Lobby full");
            Connection.getInstance().setRemoveTileId(-1);
            Player player = Player.getInstance();
            player.setPlayerId(playerId);
            player.setUsername(players[playerId]);

            Opponent opponent = Opponent.getInstance();
            opponent.setPlayerId(1-playerId);
            opponent.setUsername(players[1-playerId]);


            if (timeElapsed-savedDelta > 3) {
                IcyGame.getInstance().setScreen(new PlayScreen());
            }
        }
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        IcyGame.batch.begin();
        IcyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        IcyGame.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}