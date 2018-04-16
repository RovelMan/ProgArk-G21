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
    private int move;

    public MenuScreen(IcyGame g) {
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("NavButtons/background.png");
        move = 0;

        Image logo = new Image(new Texture("2ICYBOIIS_logo_pixelated.png"));
        Image joinBtn = new Image(new Texture("NavButtons/JOIN.png"));
        Image createBtn = new Image(new Texture("NavButtons/CREATE.png"));
        Image settingsBtn = new Image(new Texture("NavButtons/SETTINGS.png"));
        Image helpBtn = new Image(new Texture("NavButtons/HELP.png"));

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

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(logo).expandX().size(width, height/6).padBottom(20);
        table.row();
        table.add(joinBtn).expandX().size(width/3, height/8);
        table.row();
        table.add(createBtn).expandX().size(width/2, height/8);
        table.row();
        table.add(helpBtn).expandX().size(width/3, height/8).padBottom(10);
        table.row();
        table.add(settingsBtn).expandX().size(width/6, height/8);
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
        if (move < -1000) {
            move = 0;
        } else {
            move--;
        }
        game.batch.begin();
        game.batch.draw(background, 0, 0+move, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()*2);
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
