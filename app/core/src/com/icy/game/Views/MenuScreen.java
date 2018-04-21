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
import com.icy.game.Models.Button;

import java.util.HashMap;
import java.util.Map;

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

        Image logo = new Image(new Texture("Logos/2ICYBOIIS_logo_pixelated_v2.png"));

        //Buttons are easily added to this array
        String[] button_types = {"JOIN", "CREATE", "HELP", "SETTINGS"};

        Map<String, Button> buttons = new HashMap<>();

        for (String type : button_types) {
            buttons.put(type, new Button(type));
        }

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(logo).expandX().size(width, height/6).padBottom(20);
        table.row();
        table.add(buttons.get("JOIN").img).expandX().size(width/3, height/8);
        table.row();
        table.add(buttons.get("CREATE").img).expandX().size(width/2, height/8);
        table.row();
        table.add(buttons.get("HELP").img).expandX().size(width/3, height/8).padBottom(10);
        table.row();
        table.add(buttons.get("SETTINGS").img).expandX().size(width/6, height/8);
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
        IcyGame.batch.begin();
        IcyGame.batch.draw(background, 0, move, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()*2);
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
