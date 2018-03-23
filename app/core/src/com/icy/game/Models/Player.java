package com.icy.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;

import java.util.ArrayList;

/**
 * Created by havard on 13.03.18.
 */

public class Player extends TextureHolder {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle hitBox;
    private Rectangle standingOnPlatform;
    private float gravity;
    private float jumpForce;
    private boolean onGround;
    public Player(float scale, String path){
        super(scale,path);
        velocity = new Vector2(0,0);
        position = new Vector2(0,40);
        hitBox = new Rectangle(position.x,position.y,size.x,size.y);
        gravity = -100f;
        jumpForce = 1000f;
        onGround = false;
        standingOnPlatform = null;
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public boolean getOnGround(){
        return onGround;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void updateVelocity(){
        if(this.onGround) {
            this.getVelocity().y = 0;
        }
        else{
            this.getVelocity().y += this.gravity;
        }
    }

    public void updatePosition(float deltaTime){
        this.getPosition().add(this.getVelocity().x * deltaTime,this.getVelocity().y * deltaTime);
        this.hitBox.setX(this.getPosition().x);
        this.hitBox.setY(this.getPosition().y);
        if(this.getPosition().x < 0){
            this.getPosition().x = 0;
        }
        else if(this.getPosition().x > IcyGame.WIDTH-this.getSize().x){
            this.getPosition().x = IcyGame.WIDTH -this.getSize().x;
        }
    }

    public void checkPlatformCollision(ArrayList<Rectangle> platforms) {
        boolean checkCollision = true;
        if(standingOnPlatform != null){
            if(!this.hitBox.overlaps(standingOnPlatform)){
                standingOnPlatform = null;
                this.onGround = false;
            }
            else{
                checkCollision = false;
            }
        }
        if (checkCollision){
            for (Rectangle platform : platforms) {
                if(this.hitBox.overlaps(platform) && this.getVelocity().y < 0){
                    this.onGround = true;
                    this.position.y = platform.y+platform.height-1;
                    this.standingOnPlatform = platform;
                    break;
                }
            }
        }
    }

    @Override
    public Vector2 getSize() {
        return size;
    }
}
