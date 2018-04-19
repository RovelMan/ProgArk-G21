package com.icy.game.Models;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class AnimationHolder {
    Vector2 size;
    Animation<TextureRegion> animation;

    AnimationHolder(Vector2 scale, String path){
        TextureAtlas animationAtlas = new TextureAtlas(Gdx.files.internal(path));
        animation = new Animation<TextureRegion>(0.2f, animationAtlas.getRegions());
        float width = animationAtlas.getTextures().first().getWidth();
        float height = animationAtlas.getTextures().first().getHeight();
        size = new Vector2(width * scale.x, height * scale.y);
    }

    abstract public Vector2 getSize();

    abstract public Animation getAnimation();
}
