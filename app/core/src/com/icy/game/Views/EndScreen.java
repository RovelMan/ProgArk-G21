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
import com.icy.game.IcyGame;
import com.icy.game.Models.Button;
import com.icy.game.Models.Opponent;
import com.icy.game.Models.Player;

import java.util.HashMap;
import java.util.Map;


public class EndScreen implements Screen {

    private Stage stage;
    private Texture background;

    EndScreen(int winner) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        background = new Texture("Backgrounds/default_background.png");

        Label gameOverTxt = new Label("Game over", new Label.LabelStyle(IcyGame.font, Color.WHITE));
        String winnerName;
        if (winner == 1) {
            Player player = Player.getInstance();
            winnerName = player.getUsername();
        } else {
            Opponent opponent = Opponent.getInstance();
            winnerName = opponent.getUsername();
        }
        Label playerWonTxt = new Label("Player " + winnerName + " won!", new Label.LabelStyle(IcyGame.font, Color.WHITE));

        //Buttons are easily added to this array
        String[] button_types = {"REMATCH", "QUIT"};

        Map<String, Button> buttons = new HashMap<>();

        for (String type : button_types) {
            buttons.put(type, new Button(type));
        }

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.add(gameOverTxt).expandX().size(width/2, height/6);
        table.row();
        table.add(playerWonTxt).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(buttons.get("REMATCH").img).expandX().padBottom(10).size(width/2, height/8);
        table.row();
        table.add(buttons.get("QUIT").img).expandX().size(width/3, height/8);
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
        background.dispose();
        stage.dispose();
    }
}