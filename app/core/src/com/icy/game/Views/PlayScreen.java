package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icy.game.IcyGame;
import com.icy.game.Models.Player;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScreen implements Screen {

    private static IcyGame game;
    private Player player1;
    private Player player2;
    private OrthographicCamera cam;
    private Viewport viewport;
    private float timeElapsed;
    private int playerId;

    private OrthogonalTiledMapRenderer renderer;
    private Map<String,ArrayList<Rectangle>> hitboxes;
    private Map<String,TiledMapTileLayer> tileLayers;
    private static final List<String> validHitboxes =
            Collections.unmodifiableList(Arrays.asList("platformsHitbox", "logPlatformsHitbox","coinsHitbox"));
    private static final List<String> validTileLayers =
            Collections.unmodifiableList(Arrays.asList("platforms", "logPlatforms","coins"));

    PlayScreen(IcyGame g, int playerId) {
        game = g;
        this.playerId = playerId;
        player1 = new Player(new Vector2(0.07f,0.5f),"running_animation/running_animation.atlas",game);
        player2 = new Player(new Vector2(0.07f,0.5f),"player2_running/p2_run_anim.atlas",game);
        cam = new OrthographicCamera();
        //worldWidth and worldHeight is NOT the worlds width and height! They are just the size
        //of your viewport...
        viewport = new FitViewport(IcyGame.WIDTH,IcyGame.HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("Map V2/new_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        hitboxes = new HashMap<String, ArrayList<Rectangle>>();
        tileLayers = new HashMap<String, TiledMapTileLayer>();
        for (MapLayer layer: map.getLayers()) {
            if(validHitboxes.contains(layer.getName())){
                hitboxes.put(layer.getName(),new ArrayList<Rectangle>());
                for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)){
                    hitboxes.get(layer.getName()).add(((RectangleMapObject)object).getRectangle());
                }
            }
            if(validTileLayers.contains(layer.getName())){
                tileLayers.put(layer.getName(),(TiledMapTileLayer)layer);
            }
        }
        game.soundController.playMusic("music");
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    public void handleInput() {

        if(IcyGame.USEDEBUG){

            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                player1.getVelocity().x = 500;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.A)){
                player1.getVelocity().x = -500;
            }
            else{
                player1.getVelocity().x = 0;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && player1.isOnGround()){
                player1.jump();
            }

        }
        else{
            if (Gdx.input.justTouched()) {
                if(player1.isOnGround()){
                    player1.jump();
                }
            }
            player1.getVelocity().x = Gdx.input.getRoll()*15;
        }
    }

    public void update(float deltaTime) {

        handleInput();
        player1.updateVelocity();
        player1.updatePosition(deltaTime);
        try {
            System.out.println(this.playerId);
            game.connection.sendPosition(
                game.connection.getRoomName(),
                this.playerId,
                player1.getPosition(),
                player1.getVelocity()
            );
        } catch (JSONException e) {
            System.out.println("Something wen't wrong. Ups");
        }

        if(player1.getPosition().y + player1.getSize().y < cam.position.y-cam.viewportHeight/2 ){
            game.soundController.removeMusic("music");
            game.setScreen(new MenuScreen(game));
        }

        player2.getPosition().x = game.connection.getOpponentPos().x;
        player2.getVelocity().x = game.connection.getOpponentVel().x;
        player2.getPosition().y = game.connection.getOpponentPos().y;
        player2.getVelocity().y = game.connection.getOpponentVel().y;

        player1.checkPlatformCollision(hitboxes.get("platformsHitbox"));
        //this can be moved into the players coin collision checker when the PlayScreen is converted to singleton
        ArrayList<Rectangle> coins = hitboxes.get("coinsHitbox");
        int removeID = player1.checkCoinCollision(coins);
        if(removeID != -1){
            int x = Math.round(coins.get(removeID).getX()/32);
            int y = Math.round(coins.get(removeID).getY()/32);
            tileLayers.get("coins").getCell(x,y).setTile(null);
            coins.remove(removeID);
        }

        cam.position.y += 1;
        cam.update();
        renderer.setView(cam);
        timeElapsed += deltaTime;

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        TextureRegion frame1 = (TextureRegion) player1.getAnimation().getKeyFrame(timeElapsed,true);
        TextureRegion frame2 = (TextureRegion) player2.getAnimation().getKeyFrame(timeElapsed,true);
        boolean flip1 = (player1.getDirection() == -1);
        boolean flip2 = (player2.getDirection() == -1);
        game.batch.draw(
                frame1,
                flip1 ?  player1.getPosition().x + player1.getSize().x :
                        player1.getPosition().x,
                        player1.getPosition().y,
                flip1 ? -player1.getSize().x :
                        player1.getSize().x,
                        player1.getSize().y
        );
        game.batch.draw(
                frame2,
                flip2 ?  player2.getPosition().x + player2.getSize().x :
                        player2.getPosition().x,
                player2.getPosition().y,
                flip2 ? -player2.getSize().x :
                        player2.getSize().x,
                player2.getSize().y
        );
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
