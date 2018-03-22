package com.icy.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {

	//Change this to "true" to get same screen-size as an mobile device
	public static final boolean USEDEBUG = false;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT, WIDTH;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		HEIGHT = 800;
		WIDTH = 450;
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
