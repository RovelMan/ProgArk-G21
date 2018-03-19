package com.icy.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;

/**
 * Created by havard on 13.03.18.
 */

public class Player {
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;
    private Rectangle hitBox;
    private float gravity;
    private float jumpForce;
    private boolean onGround;
    public Player(){
        texture = new Texture("badlogic.jpg");
        velocity = new Vector2(0,0);
        position = new Vector2(IcyGame.WIDTH/2, IcyGame.HEIGHT/2);
        hitBox = new Rectangle(position.x,position.y,texture.getWidth(),texture.getHeight());
        gravity = -1500f;
        jumpForce = 900f;
        onGround = true;
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
            this.getVelocity().y += this.gravity * deltaTime;
        }
        this.getVelocity().x = -Gdx.input.getAccelerometerX() * deltaTime * 20000;
    }

    public void updatePosition(float deltaTime){
        this.getPosition().add(this.getVelocity().x * deltaTime,this.getVelocity().y * deltaTime);
        this.hitBox.setX(this.getPosition().x);
        this.hitBox.setY(this.getPosition().y);
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
        }
        else{
            onGround = false;
        }
    }
}
