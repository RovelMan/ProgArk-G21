package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class MenuScreen extends Screen {

    private boolean join, create, settings, help;
    private Stage stage;

    public MenuScreen(IcyGame game) {
        super(game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Image joinBtn = new Image(new Texture("joinBtn.png"));
        joinBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                join = true;
                return true;
            }
        });

        Image createBtn = new Image(new Texture("createBtn.png"));
        createBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                create = true;
                return true;
            }
        });
        Image settingsBtn = new Image(new Texture("settingsBtn.png"));
        settingsBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settings = true;
                return true;
            }
        });

        Image helpBtn = new Image(new Texture("helpBtn.png"));
        helpBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                help = true;
                return true;
            }
        });

        Table table = new Table();

        table.center();
        table.setFillParent(true);
        table.add(joinBtn).expandX().padBottom(10);
        table.row();
        table.add(createBtn).expandX().padBottom(10);
        table.row();
        table.add(settingsBtn).expandX().padBottom(10);
        table.row();
        table.add(helpBtn).expandX();
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void handleInput() {
        if (join) {
            game.connection.getSocket().emit("test", "heihei");
            //game.setScreen(new PlayScreen(game));
            System.out.println("Join button pressed");
            game.setScreen(new JoinScreen(game));
            dispose();
        } else if (create) {
            System.out.println("Create button pressed");
            game.setScreen(new CreateScreen(game));
            dispose();
        } else if (settings) {
            System.out.println("Settings button pressed");
            game.setScreen(new SettingsScreen(game));
            dispose();
        } else if (help) {
            System.out.println("Help button pressed");
            game.setScreen(new TutorialScreen(game));
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
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.end();

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
