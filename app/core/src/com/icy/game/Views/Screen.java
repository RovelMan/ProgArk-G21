package com.icy.game.Views;

/**
 * Created by jotde on 13.03.2018.
 */

public abstract class Screen implements com.badlogic.gdx.Screen {
    public Screen() { }
    public abstract void handleInput();
    public abstract void update(float deltaTime);
}
