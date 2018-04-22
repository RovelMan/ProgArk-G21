package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.icy.game.IcyGame;

public class TutorialScreen implements Screen {

    private static final TutorialScreen INSTANCE = new TutorialScreen();
    private int page_counter;
    private Stage stage;
    private SpriteBatch batch;
    private Texture[] tutorial_screens = {
            new Texture("TutorialScreens/tutorial_1.png"),
            new Texture("TutorialScreens/tutorial_2.png"),
            new Texture("TutorialScreens/tutorial_3.png"),
            new Texture("TutorialScreens/tutorial_4.png"),
            new Texture("TutorialScreens/tutorial_5.png")
    };

    public static TutorialScreen getInstance() {
        return INSTANCE;
    }

    private TutorialScreen() {
        page_counter = -1;
        stage = new Stage();
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    private void handleInput(){
        if(Gdx.input.justTouched()){
            page_counter++;
        }
        if (page_counter == tutorial_screens.length) {
            page_counter = 0;
            IcyGame.getInstance().setScreen(MenuScreen.getInstance());
            dispose();
        }
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
        stage.dispose();
    }
}
