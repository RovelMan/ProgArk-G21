package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;

import org.json.JSONException;

import javax.swing.SpringLayout;

/**
 * Created by jotde on 13.03.2018.
 */

public class LobbyScreen extends Screen {

    private int playerId;
    private String[] players = {null, null};
    private String room;

    private Stage stage;

    public LobbyScreen(IcyGame game, int playerId, String host, String playerTwo, String room) {
        super(game);
        this.playerId = playerId;
        this.players[playerId] = host;
        if (playerTwo != null) {
            addPlayerTwo(playerTwo);
        }
        System.out.println(playerId + " PLAYERS " + players[0] + " " + players[1]);
        this.room = room;

        stage = new Stage();

        updateLobby(playerTwo);
    }

    public void joinLobby(int playerId, String player) {
        this.players[playerId] = player;
        System.out.println("Player joined " + playerId + " " + player);
        //updateLobby(player);
    }

    public void leaveLobby() throws JSONException {
        game.connection.leaveLobby(players[playerId], room);
    }

    public void updateLobby(String playerTwo) {
        System.out.println("UPDATED: " + playerTwo);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Label lobbyTxt = new Label(String.format("Welcome to room: " + this.room), new Label.LabelStyle(font, Color.WHITE));
        Label hostName = new Label(String.format("Host: " + this.players[0]), new Label.LabelStyle(font, Color.WHITE));
        Label playerName;
        Label info;
        if (playerTwo == null) {
            playerName = new Label(String.format("You: " + this.players[0]), new Label.LabelStyle(font, Color.WHITE));
            info = new Label(String.format("Waiting for opponent..."), new Label.LabelStyle(font, Color.WHITE));
        } else if (this.players[0] != playerTwo) {
            playerName  = new Label(String.format("You: " + this.players[1]), new Label.LabelStyle(font, Color.WHITE));
            info = new Label(String.format("Opponent: " + this.players[0]), new Label.LabelStyle(font, Color.WHITE));
        } else {
            playerName = new Label(String.format("You: " + this.players[0]), new Label.LabelStyle(font, Color.WHITE));
            info = new Label(String.format("Opponent: " + playerTwo), new Label.LabelStyle(font, Color.WHITE));
        }

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(lobbyTxt).expandX().padBottom(10);
        table.row();
        table.add(hostName).expandX().padBottom(10);
        table.row();
        table.add(playerName).expandX().padBottom(10);
        table.row();
        table.add(info).expandX().padBottom(10);
        table.pack();
        stage = new Stage();
        stage.addActor(table);
    }

    public void addPlayerTwo(String username) {
        this.players[1] = username;
        updateLobby(username);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            try {
                leaveLobby();
            } catch (JSONException e) {
                System.out.println("Unable to leave..?");
            }
            game.setScreen(new MenuScreen(game));
            dispose();
        }
        if (this.players[1] == null) {
            game.connection.checkForOpponent(this);
            System.out.println("Players: " + players[0] + " " + players[1]);
        } else {
            //System.out.println("Players: " + players[0] + " " + players[1]);
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
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
