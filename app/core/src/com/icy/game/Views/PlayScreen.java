package com.icy.game.Views;

import com.badlogic.gdx.Gdx;
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
    private float timePrevious;
    private int playerId;
    private ArrayList<Integer> removedTiles;
    private OrthogonalTiledMapRenderer renderer;
    private Map<String,ArrayList<Rectangle>> hitboxes;
    private Map<String,TiledMapTileLayer> tileLayers;
    private static final List<String> validHitboxes =
            Collections.unmodifiableList(Arrays.asList("platformsHitbox", "logPlatformsHitbox","jumpPowerHitbox"));
    private static final List<String> validTileLayers =
            Collections.unmodifiableList(Arrays.asList("platforms", "logPlatforms","jumpPower"));

    PlayScreen(IcyGame g, Player player1, Player player2) {
        game = g;
        this.player2 = player1;
        this.player1 = player2;
        removedTiles = new ArrayList<>();
        cam = new OrthographicCamera();
        //worldWidth and worldHeight is NOT the worlds width and height! They are just the size
        //of your viewport...
        viewport = new FitViewport(IcyGame.WIDTH,IcyGame.HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("Map V2/new_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        hitboxes = new HashMap<>();
        tileLayers = new HashMap<>();
        for (MapLayer layer: map.getLayers()) {
            if(validHitboxes.contains(layer.getName())){
                hitboxes.put(layer.getName(),new ArrayList<>());
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

    public void update(float deltaTime) {
        timeElapsed += deltaTime;
        player1.handleInput();
        player1.updateVelocity();
        player1.updatePosition(deltaTime);
        player1.checkPlatformCollision(hitboxes.get("platformsHitbox"));

        int removeID = player1.checkPowerupCollision(hitboxes.get("jumpPowerHitbox"),"jump");
        handlePowerup(tileLayers.get("jumpPower"), "jumpPowerHitbox", removeID);
        if (timeElapsed - timePrevious > 0.03) {
            sendGameInfo(removeID);
        }
        removeID = game.connection.getRemoveTileId();
        handlePowerup(tileLayers.get("jumpPower"), "jumpPowerHitbox", removeID);

        if(player1.getPosition().y + player1.getSize().y < cam.position.y-cam.viewportHeight/2 ){
            game.setScreen(new EndScreen(game, player1, player2, 2));
        }
        if(player2.getPosition().y + player2.getSize().y < cam.position.y-cam.viewportHeight/2 ){
            game.setScreen(new EndScreen(game, player1, player2, 1));
        }

        player2.getPosition().x = game.connection.getOpponentPos().x;
        player2.getVelocity().x = game.connection.getOpponentVel().x;
        player2.getPosition().y = game.connection.getOpponentPos().y;
        player2.getVelocity().y = game.connection.getOpponentVel().y;

        if (timeElapsed > 2) {
            cam.position.y += 1;
        }
        cam.update();
        renderer.setView(cam);
        timeElapsed += deltaTime;
    }

    private void handlePowerup(TiledMapTileLayer layer, String hitboxName, final int removeID){
        ArrayList<Rectangle>hitbox = hitboxes.get(hitboxName);
        if(!removedTiles.contains(removeID) && removeID != -1){
            int x = Math.round(hitbox.get(removeID).getX()/32);
            int y = Math.round(hitbox.get(removeID).getY()/32);
            layer.getCell(x, y).setTile(null);
            hitbox.remove(removeID);
            removedTiles.add(removeID);
        }
    }

    private void sendGameInfo(final int removeId){
        try {
            game.connection.sendPosition(
                    game.connection.getRoomName(),
                    this.playerId,
                    player1.getPosition(),
                    player1.getVelocity()
            );
            if(removeId != -1){
                game.connection.sendPowerupPickup(
                        game.connection.getRoomName(),
                        this.playerId,
                        removeId
                );
            }
        } catch (JSONException e) {
            System.out.println("Something wen't wrong. Ups");
        }
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
