package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.Controller.Connection;
import com.icy.game.IcyGame;
import com.icy.game.Models.Opponent;
import com.icy.game.Models.Player;

import org.json.JSONException;

/**
 * Created by jotde on 13.03.2018.
 */

public class EndScreen implements Screen {

    private Stage stage;
    private Texture background;
    private Player player = Player.getInstance();
    private Opponent opponent = Opponent.getInstance();

    EndScreen(int winner) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/default_background.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Label gameOverTxt = new Label("Game over", new Label.LabelStyle(font, Color.WHITE));
        String winnerName;
        if (winner == 1) {
            winnerName = player.getUsername();
        } else {
            winnerName = opponent.getUsername();
        }
        Label playerWonTxt = new Label("Player " + winnerName + " won!", new Label.LabelStyle(font, Color.WHITE));

        Image rematchBtn = new Image(new Texture("Buttons/REMATCH.png"));
        Image quitBtn = new Image(new Texture("Buttons/QUIT.png"));

        //Buttons are easily added to this array
        Image[] buttons = {rematchBtn, quitBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (j == 0) {
                        System.out.println("Rematch button pressed");
                        //RESET GAME AND REMEMBER VALUES
                        try {
                            player.resetProperties();
                            opponent.resetProperties();
                            Connection.getInstance().rematch(player.getPlayerId(), player.getUsername(), opponent.getUsername(), Connection.getInstance().getRoomName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        IcyGame.getInstance().setScreen(new LobbyScreen(player.getPlayerId(), player.getUsername(), opponent.getUsername(), Connection.getInstance().getRoomName()));
                        dispose();
                    } else if (j == 1) {
                        System.out.println("Quit button pressed");
                        //END GAME AND RESET VALUES
                        try {
                            String roomname = Connection.getInstance().getRoomName();
                            Connection.getInstance().leaveLobby(player.getPlayerId(), player.getUsername(), roomname);
                            player.resetIdentity();
                            player.resetProperties();
                            opponent.resetIdentity();
                            opponent.resetProperties();
                            if (winner == 1) {
                                Connection.getInstance().gameOver(roomname, player.getUsername());
                            } else {
                                Connection.getInstance().gameOver(roomname, opponent.getUsername());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        IcyGame.getInstance().setScreen(new MenuScreen());
                    }
                    return false;
                }
            });
        }

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(gameOverTxt).expandX().size(width/2, height/6);
        table.row();
        table.add(playerWonTxt).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(rematchBtn).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(quitBtn).expandX().size(width/3, height/8);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        IcyGame.getInstance().batch.begin();
        IcyGame.getInstance().batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        IcyGame.getInstance().batch.end();
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