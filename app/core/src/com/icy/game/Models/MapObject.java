package com.icy.game.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icy.game.IcyGame;
import com.sun.security.cert.internal.x509.X509V1CertImpl;

/**
 * Created by havard on 13.03.18.
 */

public class MapObject {
    private Texture texture;
    private Vector2 position;
    private Rectangle hitBox;
    public MapObject(){
        texture = new Texture("badlogic.jpg");
        position = new Vector2(IcyGame.WIDTH/2,0);
        hitBox = new Rectangle(0,0,IcyGame.WIDTH,texture.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Texture getTexture() {
        return texture;
    }
}
