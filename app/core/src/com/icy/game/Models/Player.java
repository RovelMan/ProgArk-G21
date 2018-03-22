package com.icy.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.sun.org.apache.bcel.internal.generic.IXOR;

/**
 * Created by havard on 13.03.18.
 */

public class Player extends TextureHolder {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle hitBox;
    private float gravity;
    private float jumpForce;
    private boolean onGround;
    public Player(float scale, String path){
        super(scale,path);
        velocity = new Vector2(0,0);
        position = new Vector2(0,40);
        hitBox = new Rectangle(position.x,position.y,size.x,size.y);
        gravity = -1500f;
        jumpForce = 1000f;
        onGround = false;
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean getOnGround(){
        return onGround;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void updateVelocity(float deltaTime){
        if(this.onGround) {
            this.getVelocity().y = 0;
        }
        else{
            this.getVelocity().y += this.gravity*deltaTime;
        }
        if(!IcyGame.USEDEBUG){
            this.getVelocity().x = -Gdx.input.getAccelerometerX() * deltaTime * 20000;
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

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void checkCollision(Rectangle otherHitbox) {
        if (this.hitBox.overlaps(otherHitbox)) {
            this.onGround = true;
            this.position.y = otherHitbox.y+otherHitbox.height-2;
        }
        else{
            onGround = false;
        }
    }

    @Override
    public Vector2 getSize() {
        return size;
    }
}
