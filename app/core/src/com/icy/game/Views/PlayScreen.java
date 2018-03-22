package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icy.game.IcyGame;
import com.icy.game.Models.MapObject;
import com.icy.game.Models.Player;


/**
 * Created by jotde on 13.03.2018.
 */

public class PlayScreen extends Screen {
    private Player player1;
    private MapObject ground;
    private OrthographicCamera cam;
    private Viewport viewport;
    private Texture bg;
    PlayScreen(IcyGame game) {
        super(game);
        player1 = new Player(0.3f,"badlogic.jpg");
        ground  = new MapObject(0.3f, "badlogic.jpg");
        bg = new Texture("map5.png");
        cam = new OrthographicCamera();
        cam = new OrthographicCamera(IcyGame.WIDTH, IcyGame.HEIGHT);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
        viewport = new FitViewport(IcyGame.WIDTH,IcyGame.HEIGHT,cam);
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            if(player1.getOnGround()){
                System.out.println(player1.getPosition());
                player1.getVelocity().y = player1.getJumpForce();
                player1.setOnGround(false);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            player1.getVelocity().x = 500;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            player1.getVelocity().x = -500;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && player1.getOnGround()){
            player1.getVelocity().y = player1.getJumpForce();
            player1.setOnGround(false);
        }
        else{
            player1.getVelocity().x = 0;
        }


    }

    @Override
    public void update(float deltaTime) {
        player1.updateVelocity(deltaTime);
        player1.updatePosition(deltaTime);
        player1.checkCollision(ground.getHitBox());
        cam.position.y += 1;
        cam.update();
        handleInput();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(bg,-60,0,bg.getWidth(),bg.getHeight());
        game.batch.draw(player1.getTexture(),player1.getPosition().x,player1.getPosition().y,player1.getSize().x,player1.getSize().y);
        game.batch.draw(ground.getTexture(),ground.getPosition().x,ground.getPosition().y,ground.getSize().x,ground.getSize().y);
        game.batch.end();
        update(delta);
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
