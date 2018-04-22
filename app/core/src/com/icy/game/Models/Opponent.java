package com.icy.game.Models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.icy.game.Views.EndScreen;

/**
 * Created by havard on 19.04.18.
 */

public class Opponent extends AnimationHolder {
    private static final Opponent ourInstance = new Opponent();
    private int playerId;
    private String username;
    private Vector2 position;
    private Vector2 velocity;
    private OrthographicCamera cam = IcyGame.cam;

    public static Opponent getInstance() {
        return ourInstance;
    }

    private Opponent() {
        super(new Vector2(0.4f,1.3f),"Players/playerTwo_running/playerTwo_running.atlas");
        velocity = new Vector2(0,0);
        position = new Vector2(0,33);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void resetIdentity() {
        this.playerId = -1;
        this.username = "";
    }

    public void resetProperties(){
        this.velocity = new Vector2(0,0);
        this.position = new Vector2(0,40);
    }

    @Override
    public Vector2 getSize() {
        return size;
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }
}