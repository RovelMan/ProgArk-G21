package com.icy.game.Views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.icy.game.Models.MapObject;
import com.icy.game.Models.Player;

/**
 * Created by jotde on 13.03.2018.
 */

public class PlayScreen extends Screen {
    private Player player1;
    private MapObject ground;
    PlayScreen(IcyGame game) {
        super(game);
        player1 = new Player();
        ground  = new MapObject();
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            if(player1.getOnGround()){
                player1.getVelocity().y = player1.getJumpForce();
                player1.setOnGround(false);
            }

        }
    }

    @Override
    public void update(float deltaTime) {
        player1.updateVelocity(deltaTime);
        player1.updatePosition(deltaTime);
        player1.checkCollision(ground.getHitBox());
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(player1.getTexture(),player1.getPosition().x,player1.getPosition().y);
        game.batch.draw(ground.getTexture(),ground.getPosition().x,ground.getPosition().y);
        game.batch.end();
        update(delta);
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
