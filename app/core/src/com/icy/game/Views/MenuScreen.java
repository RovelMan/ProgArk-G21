package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class MenuScreen extends Screen {

    public Texture img;

    public MenuScreen(IcyGame game) {
        super(game);
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.println("Menu Screen touched");
            game.setScreen(new PlayScreen(game));
            dispose();
        } else {
            System.out.println("Menu Screen not touched");
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
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(img, 100, 100);
        game.batch.end();


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
