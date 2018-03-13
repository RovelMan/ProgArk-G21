package com.icy.game.Views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jotde on 13.03.2018.
 */

public abstract class State {

    public State() {

    }

    public abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();
}
