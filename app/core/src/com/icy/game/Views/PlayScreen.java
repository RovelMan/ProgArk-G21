package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class PlayScreen extends Screen {

    public PlayScreen(IcyGame game) {
        super(game);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.println("Play Screen touched");
            game.setScreen(new MenuScreen(game));
            dispose();
        } else {
            System.out.println("Play Screen not touched");
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