package com.icy.game.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.Controller.Connection;
import com.icy.game.Controller.SoundController;
import com.icy.game.IcyGame;
import com.icy.game.Views.EndScreen;

import org.json.JSONException;

import java.util.ArrayList;

import static com.icy.game.IcyGame.cam;

/**
 * Created by havard on 19.04.18.
 */

public class Player extends AnimationHolder {
    private static final Player ourInstance = new Player();
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle hitBox;
    private Rectangle standingOnPlatform;
    private float gravity;
    private float jumpForce;
    private boolean onGround;
    private int direction;
    private boolean powerJump;
    private static final int MAXYVELOCITY = 800;
    private int playerId;
    private String username;

    public static Player getInstance() {
        return ourInstance;
    }

    private Player() {
        super(new Vector2(0.5f,1f),"Players/playerOne_running/playerOne_running.atlas");
        velocity = new Vector2(0,0);
        position = new Vector2(0,33);
        hitBox = new Rectangle(position.x,position.y,size.x,size.y);
        gravity = -100f;
        jumpForce = 1500f;
        onGround = true;
        standingOnPlatform = null;
        direction = 1;
        powerJump = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    private float getJumpForce() {
        return jumpForce;
    }

    private boolean isOnGround(){
        return onGround;
    }

    public void handleInput(){
        if(IcyGame.USEDEBUG){

            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                this.velocity.x = 500;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.A)){
                this.velocity.x = -500;
            }
            else{
                this.velocity.x = 0;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                this.jump();
            }
        }
        else{
            if (Gdx.input.justTouched()) {
                jump();
            }
            this.velocity.x = Gdx.input.getRoll()*15;
        }
    }

    private void jump(){
        if(this.isOnGround()){
            this.velocity.y = this.getJumpForce();
            this.setOnGround(false);

        }
        else if(powerJump && this.velocity.y < 0){
            this.velocity.y = this.getJumpForce();
            this.powerJump = false;
            this.setOnGround(false);
        }
        SoundController.getInstance().playEffect("jump");
    }

    private void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getDirection() {
        return direction;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    private void setDirection(int direction) {
        this.direction = direction;
    }

    public void checkDeath(){
        if(getPosition().y + getSize().y < cam.position.y-cam.viewportHeight/2 ){
            cam.position.y = cam.viewportHeight/2;
            try {
                Connection.getInstance().sendDeathStatus(Connection.getInstance().getRoomName());
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            IcyGame.getInstance().setScreen(new EndScreen(Opponent.getInstance().getUsername()));
        }
    }

    public void updateVelocity(){
        if(this.onGround) {
            this.getVelocity().y = 0;
        }
        else{
            if(this.getVelocity().y > - MAXYVELOCITY){
                this.getVelocity().y += this.gravity;
            }

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
        if(this.getVelocity().x < 0){
            this.setDirection(-1);
        }
        else{
            this.setDirection(1);
        }
    }

    public int checkPowerupCollision(ArrayList<Rectangle> powerups, String type){
        for (Rectangle powerup : powerups) {
            if (this.hitBox.overlaps(powerup)) {
                if(type.equals("jump")){
                    SoundController.getInstance().playEffect("coin");
                    this.powerJump = true;
                    System.out.println(this.powerJump);
                }

                return powerups.indexOf(powerup);
            }
        }
        return -1;
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
                if(this.hitBox.overlaps(platform) && this.getVelocity().y < 0 && this.getPosition().y > platform.getY()){
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

    @Override
    public Animation getAnimation() {
        return animation;
    }
}