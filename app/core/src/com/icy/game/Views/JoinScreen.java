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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.icy.game.Controller.Connection;
import com.icy.game.IcyGame;

/**
 * Created by jotde on 13.03.2018.
 */

public class JoinScreen implements Screen {

    private TextField userInput, roomInput;
    private Stage stage;
    private Texture background;

    public JoinScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/default_background.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.fontColor = Color.WHITE;
        style.font = font;
        Label userInputTxt = new Label(String.format("Username: "), new Label.LabelStyle(font, Color.WHITE));
        userInput = new TextField("Joiner", style);
        Label roomInputTxt = new Label(String.format("Room name: "), new Label.LabelStyle(font, Color.WHITE));
        roomInput = new TextField("DefaultRoom2", style);

        Image backBtn = new Image(new Texture("Buttons/BACK.png"));
        Image joinBtn = new Image(new Texture("Buttons/JOIN.png"));

        //Buttons are easily added to this array
        Image[] buttons = {backBtn, joinBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (j == 0) {
                        System.out.println("Back button pressed");
                        IcyGame.getInstance().setScreen(new MenuScreen());
                        dispose();
                    } else if (j == 1) {
                        try {
                            Connection.getInstance().joinLobby(userInput.getText(), roomInput.getText());
                            dispose();
                        } catch (Exception e) {
                            System.out.println("Could not join a game: " + e);
                        }
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
        table.add(userInputTxt).expandX().size(width/2, height/6);
        table.row();
        table.add(userInput).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(roomInputTxt).expandX().size(width/2, height/6);
        table.row();
        table.add(roomInput).expandX().padBottom(20).size(width/2, height/8);
        table.row();
        table.add(joinBtn).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(backBtn).expandX().size(width/2, height/8);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        IcyGame.batch.begin();
        IcyGame.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        IcyGame.batch.end();
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
        background.dispose();
        stage.dispose();
    }
}
