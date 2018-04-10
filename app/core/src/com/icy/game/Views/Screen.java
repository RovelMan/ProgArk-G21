package com.icy.game.Views;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.icy.game.IcyGame;


public abstract class Screen implements com.badlogic.gdx.Screen {
    protected final IcyGame game;
    OrthographicCamera camera;
    public Screen(final IcyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
    }
    public abstract void handleInput();
    public abstract void update(float deltaTime);
}
