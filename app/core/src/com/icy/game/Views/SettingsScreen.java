package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class SettingsScreen extends Screen {

    private Stage stage;
    private CheckBox checkBox;
    private boolean backPressed;

    public SettingsScreen(IcyGame game) {
        super(game);
        AssetManager assetManager = new AssetManager();
        assetManager.load("data/flat_earth_ui.json", Skin.class);
        assetManager.finishLoading();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        checkBox = new CheckBox(" Sound", assetManager.get("data/flat_earth_ui.json", Skin.class));
        checkBox.setChecked(true);

        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!checkBox.isChecked()) {
                    IcyGame.VOLUME = 0f;
                }
            }
        });

        Image backBtn = new Image(new Texture("backBtn.jpg"));
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
        table.add(checkBox).expandX().padBottom(10).size(IcyGame.WIDTH, IcyGame.HEIGHT/2);
        table.row();
        table.row();
        table.row();
        table.add(backBtn).expandX().size(IcyGame.WIDTH, IcyGame.HEIGHT/4);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void handleInput() {
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
