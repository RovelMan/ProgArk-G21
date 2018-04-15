package com.icy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Controller.Connection;
import com.icy.game.Controller.SoundController;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {

	//Change this to "true" to use keyboard controls
	public static final boolean USEDEBUG = true;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT = 800;
	public static int WIDTH = 448;
	public SpriteBatch batch;
	public Connection connection;
	private final String URL = "http://192.168.1.2:7676";
	public SoundController soundController;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
		soundController = SoundController.getInstance();
		connection = new Connection(this, URL);
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
