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

/**
 * Created by jotde on 13.03.2018.
 */

public class EndScreen implements Screen {

    private Stage stage;
    private Connection connection;
    private static IcyGame game;
    private Texture background;

    public EndScreen(IcyGame g, Player player1, Player player2) {
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.connection = game.connection;

        background = new Texture("NavButtons/background2.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Label gameOverTxt = new Label("Game over", new Label.LabelStyle(font, Color.WHITE));
        Label playerLostTxt = new Label("Player " + player1.getUsername() + " lost", new Label.LabelStyle(font, Color.WHITE));

        Image backBtn = new Image(new Texture("NavButtons/BACK.png"));
        Image createBtn = new Image(new Texture("NavButtons/CREATE.png"));

        //Buttons are easily added to this array
        Image[] buttons = {backBtn, createBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (j == 0) {
                        System.out.println("Back button pressed");
                        game.setScreen(new MenuScreen(game));
                        dispose();
                    } else if (j == 1) {

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
