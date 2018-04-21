package com.icy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Controller.Connection;
import com.icy.game.Controller.SoundController;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {
	private static IcyGame INSTANCE;
	//Change this to "true" to use keyboard controls
	public static final boolean USEDEBUG = true;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT = 800;
	public static int WIDTH = 448;
	// Use this as volume for all sounds
	public static float VOLUME = 1.0f;
	public static SpriteBatch batch;
	public static final String URL = "http://77.66.48.113:7676";

	public static IcyGame getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		SoundController soundController = SoundController.getInstance();
		this.setScreen(new MenuScreen());
		soundController.addEffect("jump", "Sounds/effects/jump.mp3");
		soundController.addEffect("coin","Sounds/effects/coin.mp3");
		soundController.addMusic("music","Sounds/music/music.mp3");
		INSTANCE = this;
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
