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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icy.game.Controller.Connection;
import com.icy.game.Controller.SoundController;
import com.icy.game.IcyGame;
import com.icy.game.Models.Opponent;
import com.icy.game.Models.Player;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScreen implements Screen {

    private Player player = Player.getInstance();
    private Opponent opponent = Opponent.getInstance();
    private OrthographicCamera cam = IcyGame.cam;
    private Viewport viewport = IcyGame.viewport;
    private float timeElapsed;
    private ArrayList<Integer> removedTiles;
    private OrthogonalTiledMapRenderer renderer;
    private Map<String,ArrayList<Rectangle>> hitboxes;
    private Map<String,TiledMapTileLayer> tileLayers;
    private static final List<String> validHitboxes =
            Collections.unmodifiableList(Arrays.asList("platformsHitbox", "logPlatformsHitbox","jumpPowerHitbox"));
    private static final List<String> validTileLayers =
            Collections.unmodifiableList(Arrays.asList("platforms", "logPlatforms","jumpPower"));

    PlayScreen() {
        removedTiles = new ArrayList<>();
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("Maps/map_1.tmx");
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
        SoundController.getInstance().playMusic("music");
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    private void update(float deltaTime) {
        timeElapsed += deltaTime;
        player.handleInput();
        player.updateVelocity();
        player.updatePosition(deltaTime);
        player.checkPlatformCollision(hitboxes.get("platformsHitbox"));
        player.checkDeath();

        int removeID = player.checkPowerupCollision(hitboxes.get("jumpPowerHitbox"),"jump");
        handlePowerup(tileLayers.get("jumpPower"), "jumpPowerHitbox", removeID);
        sendGameInfo(removeID);
        removeID = Connection.getInstance().getRemoveTileId();
        handlePowerup(tileLayers.get("jumpPower"), "jumpPowerHitbox", removeID);

        if (timeElapsed > 2) {
            cam.position.y += 1;
        }
        cam.update();
        renderer.setView(cam);
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
            Connection.getInstance().sendPosition(
                    Connection.getInstance().getRoomName(),
                    player.getPlayerId(),
                    player.getPosition(),
                    player.getVelocity()
            );
            if(removeId != -1){
                Connection.getInstance().sendPowerupPickup(
                        Connection.getInstance().getRoomName(),
                        player.getPlayerId(),
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
        IcyGame.getInstance().batch.setProjectionMatrix(cam.combined);
        TextureRegion frame1 = (TextureRegion) player.getAnimation().getKeyFrame(timeElapsed,true);
        TextureRegion frame2 = (TextureRegion) opponent.getAnimation().getKeyFrame(timeElapsed,true);
        boolean flip1 = (player.getDirection() == 1);
        IcyGame.getInstance().batch.begin();
        IcyGame.getInstance().batch.draw(
                frame1,
                flip1 ?  player.getPosition().x + player.getSize().x :
                        player.getPosition().x,
                player.getPosition().y,
                flip1 ? -player.getSize().x :
                        player.getSize().x,
                player.getSize().y
        );
        IcyGame.getInstance().batch.draw(frame2,
                opponent.getPosition().x,
                opponent.getPosition().y,
                opponent.getSize().x,
                opponent.getSize().y);
        IcyGame.getInstance().batch.end();

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