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
import com.icy.game.IcyGame;

import org.json.JSONException;

public class LobbyScreen implements Screen {

    private static int playerId;
    private static String[] players = {null, null};
    private static String room;
    private static IcyGame game;
    private Texture background;

    private static Stage stage;

    public LobbyScreen(IcyGame g, int newPlayerId, String host, String playerTwo, String roomName) {
        playerId = newPlayerId;
        players[0] = host;
        game = g;
        Gdx.input.setInputProcessor(stage);
        if (playerTwo != null) {
            addPlayerTwo(playerTwo);
        }
        System.out.println(playerId + 1 + " PLAYERS IN ROOM " + players[0] + " " + players[1]);
        room = roomName;

        background = new Texture("NavButtons/background2.png");

        stage = new Stage();
        updateLobby(playerTwo);
    }

    public void joinLobby(int playerId, String player) {
        players[playerId] = player;
        System.out.println("Player joined " + playerId + " " + player);
    }

    private void leaveLobby() throws JSONException {
        game.connection.leaveLobby(players[playerId], room);
    }

    private void updateLobby(String playerTwo) {
        System.out.println("UPDATED: " + playerTwo);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Label lobbyTxt = new Label("Welcome to room: " + room, new Label.LabelStyle(font, Color.WHITE));
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (players[0] != null && players[1] != null) {
            System.out.println("Both players joined. Lobby full");
            game.setScreen(new PlayScreen(game, this.playerId));
        }

        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
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

    }
}
