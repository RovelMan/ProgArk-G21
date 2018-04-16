package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class SettingsScreen extends Screen {

    private Stage stage;
    private boolean backPressed, soundOff, checkboxTouched;
    private Image checked, unchecked;

    public SettingsScreen(IcyGame game) {
        super(game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5);
        Label soundLabel = new Label("Sound", new Label.LabelStyle(font, Color.WHITE));

        checked = new Image(new Texture("Buttons/Checkbox/checkBox_checked.png"));
        unchecked = new Image(new Texture("Buttons/Checkbox/checkBox_unchecked.png"));

        checked.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                soundOff = true;
                checkboxTouched = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                soundOff = false;
                checkboxTouched = false;
            }
        });

        Image backBtn = new Image(new Texture("backBtn.png"));
        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backPressed = true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backPressed = false;
            }
        });

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(checked).padBottom(10).size(IcyGame.WIDTH / 8, IcyGame.HEIGHT / 2);
        table.add(soundLabel).padBottom(10).size(IcyGame.WIDTH / 2, IcyGame.HEIGHT / 2);
        table.row();
        table.row();
        table.row();
        table.add(backBtn).expandX().size(IcyGame.WIDTH, IcyGame.HEIGHT/4);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void handleInput() {
        if (checkboxTouched) {
            if (soundOff) {
                checked = unchecked;
                IcyGame.VOLUME = 0;
            }
        }
        if (backPressed) {
            System.out.println("Back button pressed");;
            game.setScreen(new MenuScreen(game));
            dispose();
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
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
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
