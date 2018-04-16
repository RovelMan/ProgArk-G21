package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class TutorialScreen implements Screen{

    private IcyGame game;
    private int page_counter;
    private Viewport viewport;
    private Stage stage;
    private Texture tutorial_1 = new Texture("tutorial/tutorial_1.png");
    private Texture tutorial_2 = new Texture("tutorial/tutorial_2.png");
    private Texture tutorial_3 = new Texture("tutorial/tutorial_3.png");
    private Texture tutorial_4 = new Texture("tutorial/tutorial_4.png");
    private SpriteBatch batch = new SpriteBatch();
    private Texture[] tutorial_screens = {tutorial_1, tutorial_2, tutorial_3, tutorial_4};

    public TutorialScreen(IcyGame game) {
        this.game = game;
        page_counter = -1;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        batch.begin();
        batch.draw(tutorial_screens[page_counter], 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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

    public void handleInput(){
        System.out.print(page_counter);
        if(Gdx.input.justTouched()){
            page_counter++;
        }
        if (page_counter == 4) {
            page_counter = 0;
            game.setScreen(new MenuScreen(game));
            dispose();
        }

    }

}
