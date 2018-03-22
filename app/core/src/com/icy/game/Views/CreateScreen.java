package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
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

public class CreateScreen extends Screen {

    private TextField userInput, roomInput;
    private boolean[] btnPressed = {false, false};
    private Stage stage;
    private Connection connection;

    public CreateScreen(IcyGame game) {
        super(game);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.connection = game.connection;

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.fontColor = Color.WHITE;
        style.font = new BitmapFont();
        Label userInputTxt = new Label(String.format("Username: "), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        userInput = new TextField("_", style);
        Label roomInputTxt = new Label(String.format("Room name: "), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        roomInput = new TextField("_", style);

        Image backBtn = new Image(new Texture("backBtn.png"));
        Image createBtn = new Image(new Texture("createBtn.png"));

        //Buttons are easily added to this array
        Image[] buttons = {backBtn, createBtn};

        for (int i = 0; i < buttons.length; i++) {
            final int j = i;
            buttons[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    btnPressed[j] = true;
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    btnPressed[j] = false;
                }
            });
        }

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(userInputTxt).expandX().padBottom(10);
        table.add(userInput).expandX().padBottom(10);
        table.row();
        table.add(roomInputTxt).expandX().padBottom(10);
        table.add(roomInput).expandX().padBottom(20);
        table.row();
        table.add(backBtn).expandX().padBottom(10);
        table.add(createBtn).expandX();
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void handleInput() {
        if (btnPressed[0]) {
            System.out.println("Back button pressed");
            game.setScreen(new MenuScreen(game));
            dispose();
        } else if (btnPressed[1]) {
            try {
                connection.createLobby(userInput.getText(), roomInput.getText());
                System.out.println("Lobby created!");
                System.out.println("PlayerId: " + connection.getPlayerId() + "\tUsername: " + userInput.getText() + "\tRoom: " + roomInput.getText());
            } catch (Exception e) {
                System.out.println("Could not create a game: " + e);
            }
            game.setScreen(new LobbyScreen(game, connection.getPlayerId(), userInput.getText(), roomInput.getText()));
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
        Gdx.gl.glClearColor(1, 1, 0, 1);
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
