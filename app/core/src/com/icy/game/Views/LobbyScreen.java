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

    private static final LobbyScreen INSTANCE = new LobbyScreen();
    private Texture background;
    private float savedDelta = -1;
    private float timeElapsed;
    private static Stage stage;
    private String room;

    public static LobbyScreen getInstance() {
        return INSTANCE;
    }

    private LobbyScreen() {

    }

    private void updateLobby(String playerTwo) {
        System.out.println("UPDATED: " + playerTwo);

        BitmapFont font = IcyGame.font;

        Label lobbyTxt = new Label("Room name: " + room, new Label.LabelStyle(font, Color.WHITE));
        Label hostName = new Label("Opponent: " + Opponent.getInstance().getUsername(), new Label.LabelStyle(font, Color.WHITE));
        Label playerName;
        Label info;
        if (playerTwo == null) {
            playerName = new Label("You: " + Player.getInstance().getUsername(), new Label.LabelStyle(font, Color.WHITE));
            info = new Label("Waiting for opponent...", new Label.LabelStyle(font, Color.WHITE));
        } else {
            playerName = new Label("You: " + Player.getInstance().getUsername(), new Label.LabelStyle(font, Color.WHITE));
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

    @Override
    public void show() {

        Connection conn = Connection.getInstance();
        room = conn.getRoomName();

        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/default_background.png");

        stage = new Stage();
        updateLobby(Opponent.getInstance().getUsername());
    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;
        if (Opponent.getInstance().getUsername() != null) {
            if (savedDelta == -1) {
                savedDelta = timeElapsed;
            }
            System.out.println("Both players joined. Lobby full");
            Connection.getInstance().setRemoveTileId(-1);
            if (timeElapsed-savedDelta > 1.5) {
                IcyGame.getInstance().setScreen(PlayScreen.getInstance());
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