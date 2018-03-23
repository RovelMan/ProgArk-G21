package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icy.game.IcyGame;
import com.icy.game.Models.Player;

import java.util.ArrayList;


/**
 * Created by jotde on 13.03.2018.
 */

public class PlayScreen extends Screen {
    private Player player1;
    private OrthographicCamera cam;
    private Viewport viewport;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private ArrayList<Rectangle> platforms;

    PlayScreen(IcyGame game) {
        super(game);
        player1 = new Player(0.1f,"badlogic.jpg");
        cam = new OrthographicCamera();

        //worldWidth and worldHeight is NOT the worlds width and height! They are just the size
        //of your viewport...
        viewport = new FitViewport(IcyGame.WIDTH,IcyGame.HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/Files/Files/new_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        platforms = new ArrayList<Rectangle>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            platforms.add(((RectangleMapObject)object).getRectangle());
        }
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    @Override
    public void handleInput() {

        if(IcyGame.USEDEBUG){

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
        else{
            if (Gdx.input.justTouched()) {
                if(player1.getOnGround()){
                    player1.getVelocity().y = player1.getJumpForce();
                    player1.setOnGround(false);
                }
            }
            player1.getVelocity().x = -Gdx.input.getAccelerometerX() * 200;
        }
    }

    @Override
    public void update(float deltaTime) {

        handleInput();
        player1.updateVelocity();
        player1.updatePosition(deltaTime);
        player1.checkPlatformCollision(platforms);
        cam.position.y += 0.5;
        cam.update();
        renderer.setView(cam);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        game.batch.draw(player1.getTexture(),player1.getPosition().x,player1.getPosition().y,player1.getSize().x,player1.getSize().y);
        game.batch.end();
    }

    @Override
    public void show() {

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
