package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.icy.game.IcyGame;
import com.sun.org.apache.bcel.internal.generic.IXOR;

import javax.xml.soap.Text;

import static java.lang.Math.round;

/**
 * Created by jotde on 13.03.2018.
 */

public class MenuScreen extends Screen {

    //Keeps track of which button gets pressed
    private boolean[] btnPressed = {false, false, false, false};
    private Stage stage;
    private Texture background;

    public MenuScreen(IcyGame game) {
        super(game);
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
                    btnPressed[j] = true;
                    return true;
                }
            });
        }

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(joinBtn).expandX().size(IcyGame.WIDTH, IcyGame.HEIGHT/15);
        table.row();
        table.add(createBtn).expandX().size(IcyGame.WIDTH, IcyGame.HEIGHT/15);
        table.row();
        table.add(helpBtn).expandX().size(IcyGame.WIDTH, IcyGame.HEIGHT/15);
        table.row();
        table.add(settingsBtn).expandX().size(IcyGame.WIDTH/3, IcyGame.HEIGHT/25);
        table.pack();
        stage.addActor(table);
    }

    @Override
    public void handleInput() {
        if (btnPressed[0]) {
            //game.connection.getSocket().emit("test", "heihei");
            System.out.println("Join button pressed");
            game.setScreen(new JoinScreen(game));
            //game.setScreen(new PlayScreen(game));
            dispose();
        } else if (btnPressed[1]) {
            System.out.println("Create button pressed");
            game.setScreen(new CreateScreen(game));
            dispose();
        } else if (btnPressed[2]) {
            System.out.println("Help button pressed");
            game.setScreen(new TutorialScreen(game));
            dispose();
        } else if (btnPressed[3]) {
            System.out.println("Settings button pressed");
            game.setScreen(new SettingsScreen(game));
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
        game.batch.draw(background, 0, 0, round(IcyGame.WIDTH*4.28), round(IcyGame.HEIGHT/2.5));
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
