package com.icy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Controller.Connection;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {

	//Change this to "true" to get same screen-size as an mobile device
	public static final boolean USEDEBUG = false;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT, WIDTH;
	public SpriteBatch batch;

	public Connection connection;
	private static final String URL = "http://localhost:7676";
	
	@Override
	public void create () {
		HEIGHT = 800;
		WIDTH = 450;
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
		connection = new Connection(URL);
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
