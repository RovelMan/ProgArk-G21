package com.icy.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;

/**
 * Created by havard on 13.03.18.
 */

public class MapObject{
    private Vector2 position;
    private Rectangle hitBox;
    public MapObject(float scale, String path){

    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
