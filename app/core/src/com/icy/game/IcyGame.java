package com.icy.game;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.icy.game.Controller.SoundController;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {
	private static IcyGame INSTANCE;
	//Change this to "true" to use keyboard controls
	public static final boolean USEDEBUG = false;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT = 800;
	public static int WIDTH = 448;
	// Use this as volume for all sounds
	public static float VOLUME = 1.0f;
	public static BitmapFont font;
	public static TextField.TextFieldStyle style = new TextField.TextFieldStyle();
	public static SpriteBatch batch;
	public static OrthographicCamera cam;
	public static Viewport viewport;
	public static final String URL = "http://10.22.43.128:7676";
	
	public static IcyGame getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(4);
		style.fontColor = Color.WHITE;
		style.font = font;
		SoundController soundController = SoundController.getInstance();
		soundController.addEffect("jump", "Sounds/effects/jump.mp3");
		soundController.addEffect("coin", "Sounds/effects/coin.mp3");
		soundController.addMusic("music", "Sounds/music/music.mp3");
		soundController.addMusic("menu_music", "Sounds/music/menu_music.mp3");
		this.setScreen(new MenuScreen());
		cam = new OrthographicCamera();
		//worldWidth and worldHeight is NOT the worlds width and height! They are just the size
		//of your viewport...
		viewport = new FitViewport(IcyGame.WIDTH,IcyGame.HEIGHT, cam);
		cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
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
