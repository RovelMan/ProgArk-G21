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
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class SettingsScreen implements Screen {

    private Stage stage;
    private Image audioOn, audioOff;
    private static IcyGame game;

    public SettingsScreen(IcyGame g) {
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);
        Label audioLabel = new Label("Audio", new Label.LabelStyle(font, Color.WHITE));

        audioOn = new Image(new Texture("Buttons/Audio/audio_on.png"));
        audioOn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Audio on");
                IcyGame.VOLUME = 1.0f;
                System.out.println(IcyGame.VOLUME);
                return true;
            }
        });
        audioOff = new Image(new Texture("Buttons/Audio/audio_off.png"));
        audioOff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Audio off");
                IcyGame.VOLUME = 0;
                game.soundController.stopMusic("music");
                System.out.println(IcyGame.VOLUME);
                return true;
            }
        });


        Image backBtn = new Image(new Texture("NavButtons/BACK.png"));
        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Back button pressed");
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(audioLabel).padBottom(10).padRight(10).size(IcyGame.WIDTH / 6, IcyGame.HEIGHT / 8);
        table.add(audioOn).padBottom(10).size(IcyGame.WIDTH / 6, IcyGame.HEIGHT / 8);
        table.add(audioOff).padBottom(10).size(IcyGame.WIDTH / 6, IcyGame.HEIGHT / 8);
        table.row();
        table.row();
        table.row();
        table.add(backBtn).padTop(50).center().expandX().size(IcyGame.WIDTH / 2, IcyGame.HEIGHT / 8);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
