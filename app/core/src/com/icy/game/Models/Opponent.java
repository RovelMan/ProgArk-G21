package com.icy.game.Models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.icy.game.Views.EndScreen;

public class Opponent extends AnimationHolder {
    private static final Opponent INSTANCE = new Opponent();
    private String username = null;
    private static Vector2 position;
    private static Vector2 velocity;
    private OrthographicCamera cam = IcyGame.cam;

    public static Opponent getInstance() {
        return INSTANCE;
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

    public void setVelocity(Vector2 vel) {
        velocity = vel;
    }

    public void setPosition(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void resetIdentity() {
        this.username = null;
    }

    public void resetProperties(){
        velocity = new Vector2(0,0);
        position = new Vector2(0,40);
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