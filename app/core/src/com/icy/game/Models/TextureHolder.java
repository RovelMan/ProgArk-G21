package com.icy.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;

/**
 * Created by havard on 22.03.18.
 */

public abstract class TextureHolder {
    Vector2 size;
    float scale;
    Texture texture;
    TextureHolder(float newScale,String path){
        texture = new Texture(path);
        scale = newScale;
        size = new Vector2(IcyGame.WIDTH*scale,texture.getHeight()-(texture.getWidth()-IcyGame.WIDTH*scale));
    }

    abstract public Vector2 getSize();
}
