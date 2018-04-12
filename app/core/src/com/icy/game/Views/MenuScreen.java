package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;

import static java.lang.Math.round;

public class MenuScreen implements Screen {

    private Stage stage;
    private Texture background;
    private IcyGame game;

    public MenuScreen(IcyGame g) {
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("background.png");

        Image joinBtn = new Image(new Texture("Buttons/JoinBtn/JoinBtn_1.png"));
        Image createBtn = new Image(new Texture("Buttons/CreateBtn/CreateBtn_1.png"));
        Image settingsBtn = new Image(new Texture("Buttons/SettingsBtn.png"));
        Image helpBtn = new Image(new Texture("Buttons/HelpBtn/HelpBtn_1.png"));

        //Buttons are easily added to this array
        Image[] buttons = {joinBtn, createBtn, helpBtn, settingsBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (j == 0) {
                        System.out.println("Join button pressed");
                        game.setScreen(new JoinScreen(game));
                        dispose();
                    } else if (j == 1) {
                        System.out.println("Create button pressed");
                        game.setScreen(new CreateScreen(game));
                        dispose();
                    } else if (j == 2) {
                        System.out.println("Help button pressed");
                        game.setScreen(new TutorialScreen(game));
                        dispose();
                    } else if (j == 3) {
                        System.out.println("Settings button pressed");
                        game.setScreen(new PlayScreen(game, 0));
                        dispose();
                    }
                    return false;
                }
            });
        }

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(joinBtn).expandX().size(IcyGame.WIDTH/4, IcyGame.HEIGHT/6);
        table.row();
        table.add(createBtn).expandX().size(IcyGame.WIDTH/4, IcyGame.HEIGHT/6);
        table.row();
        table.add(helpBtn).expandX().size(IcyGame.WIDTH/4, IcyGame.HEIGHT/6);
        table.row();
        table.add(settingsBtn).expandX().size(IcyGame.WIDTH/4, IcyGame.HEIGHT/6);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, round(Gdx.graphics.getWidth()), round(Gdx.graphics.getHeight()));
        game.batch.end();

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
