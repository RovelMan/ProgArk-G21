package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.icy.game.IcyGame;
import com.icy.game.Models.Button;

import java.util.HashMap;
import java.util.Map;

public class CreateScreen implements Screen {

    private Stage stage;
    private Texture background;

    public CreateScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/default_background.png");

        Label userInputTxt = new Label("Username: ", new Label.LabelStyle(IcyGame.font, Color.WHITE));
        TextField userInput = new TextField("Creator", IcyGame.style);
        Label roomInputTxt = new Label("Room name: ", new Label.LabelStyle(IcyGame.font, Color.WHITE));
        TextField roomInput = new TextField("DefaultRoom2", IcyGame.style);

        //Buttons are easily added to this array
        String[] button_types = {"BACK", "CREATELOBBY"};

        Map<String, Button> buttons = new HashMap<>();

        for (String type : button_types) {
            if (type.equals("CREATELOBBY")) {
                buttons.put(type, new Button(type, userInput.getText(), roomInput.getText()));
            } else {
                buttons.put(type, new Button(type));
            }
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
        table.add(buttons.get("CREATELOBBY").img).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(buttons.get("BACK").img).expandX().size(width/2, height/8);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
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

    }
}