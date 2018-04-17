package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.icy.game.Controller.Connection;
import com.icy.game.IcyGame;
import com.icy.game.Models.Player;

import org.json.JSONException;

/**
 * Created by jotde on 13.03.2018.
 */

public class EndScreen implements Screen {

    private Stage stage;
    private Connection connection;
    private static IcyGame game;
    private Texture background;
    private Player player1;
    private Player player2;
    private String room;

    public EndScreen(IcyGame g, Player player1, Player player2, int winner, String room) {
        game = g;
        this.player1 = player1;
        this.player2 = player2;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.connection = game.connection;

        background = new Texture("NavButtons/background2.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Label gameOverTxt = new Label("Game over", new Label.LabelStyle(font, Color.WHITE));
        String winnerName;
        if (winner == 1) {
            winnerName = player1.getUsername();
        } else {
            winnerName = player2.getUsername();
        }
        Label playerWonTxt = new Label("Player " + winnerName + " won!", new Label.LabelStyle(font, Color.WHITE));

        Image rematchBtn = new Image(new Texture("NavButtons/REMATCH.png"));
        Image quitBtn = new Image(new Texture("NavButtons/QUIT.png"));

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
                        game.setScreen(new LobbyScreen(game, player1.getPlayerId(), player1.getUsername(), player2.getUsername(), room));
                        dispose();
                    } else if (j == 1) {
                        System.out.println("Quit button pressed");
                        //END GAME AND RESET VALUES
                        try {
                            connection.leaveLobby(player1.getUsername(), room);
                        } catch (JSONException e) {
                            System.out.println("You can't leave!");
                        }
                        game.setScreen(new MenuScreen(game));
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
