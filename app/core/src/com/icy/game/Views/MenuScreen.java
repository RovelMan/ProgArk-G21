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
import com.icy.game.Controller.SoundController;
import com.icy.game.IcyGame;

import java.awt.Font;

import static java.lang.Math.round;

public class MenuScreen implements Screen {

    private Stage stage;
    private Texture background;
    private int move;
    public MenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("Backgrounds/menu_background.png");
        move = 0;
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        Image logo = new Image(new Texture("Logos/2ICYBOIIS_logo_pixelated.png"));
        Image joinBtn = new Image(new Texture("Buttons/JOIN.png"));
        Image createBtn = new Image(new Texture("Buttons/CREATE.png"));
        Image helpBtn = new Image(new Texture("Buttons/HELP.png"));

        //Buttons are easily added to this array
        Image[] buttons = {joinBtn, createBtn, helpBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (j == 0) {
                        System.out.println("Join button pressed");
                        IcyGame.getInstance().setScreen(new JoinScreen());
                        dispose();
                    } else if (j == 1) {
                        System.out.println("Create button pressed");
                        IcyGame.getInstance().setScreen(new CreateScreen());
                        dispose();
                    } else if (j == 2) {
                        System.out.println("Help button pressed");
                        IcyGame.getInstance().setScreen(new TutorialScreen());
                        dispose();
                    }
                    return false;
                }
            });
        }

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Image audioOn = new Image(new Texture("Buttons/audio_on.png"));
        audioOn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Audio on");
                IcyGame.VOLUME = 1.0f;
                System.out.println(IcyGame.VOLUME);
                return true;
            }
        });
        Image audioOff = new Image(new Texture("Buttons/audio_off.png"));
        audioOff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Audio off");
                IcyGame.VOLUME = 0;
                SoundController.getInstance().stopMusic("music");
                System.out.println(IcyGame.VOLUME);
                return true;
            }
        });


        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(logo).expandX().size(width, height/6).padBottom(20);
        table.row();
        table.add(joinBtn).expandX().size(width/3, height/8);
        table.row();
        table.add(createBtn).expandX().size(width/2, height/8);
        table.row();
        table.add(helpBtn).expandX().size(width/3, height/8).padBottom(30);
        table.row();

        Table subTable = new Table();
        subTable.add(audioOn).padRight(width/24).padLeft(width/3).padBottom(30).size(IcyGame.WIDTH / 8, IcyGame.HEIGHT / 12);
        subTable.add(audioOff).padLeft(width/24).padRight(width/3).padBottom(30).size(IcyGame.WIDTH / 8, IcyGame.HEIGHT / 12);
        subTable.pack();

        table.add(subTable);
        table.row();
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
        IcyGame.getInstance().batch.begin();
        IcyGame.getInstance().batch.draw(background, 0, 0+move, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()*2);
        IcyGame.getInstance().batch.end();

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
