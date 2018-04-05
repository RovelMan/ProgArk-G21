package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class LobbyScreen extends Screen {

    private String[] players = {null, null};
    private String room;

    private Stage stage;

    public LobbyScreen(IcyGame game, int playerId, String host, String room) {
        super(game);
        this.players[playerId] = host;
        System.out.println(playerId + " PLAYERS " + players[0] + " " + players[1]);
        this.room = room;
        stage = new Stage();

        Label lobbyTxt = new Label(String.format("Welcome to room: " + this.room), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label hostName = new Label(String.format("Host: " + this.players[0]), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label info = new Label(String.format("Waiting for opponent..."), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(lobbyTxt).expandX().padBottom(10);
        table.row();
        table.add(hostName).expandX().padBottom(10);
        table.row();
        table.add(info).expandX().padBottom(10);
        table.pack();
        stage.addActor(table);
    }

    public void joinLobby(int playerId, String player) {
        this.players[playerId] = player;
        System.out.println("Player joined " + player);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            //game.setScreen(new MenuScreen(game));
            //dispose();
        }
        game.connection.checkForOpponent();
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
