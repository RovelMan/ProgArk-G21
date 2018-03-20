package com.icy.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {

	//Change this to "true" to get same screen-size as an mobile device
	public static final boolean USEDEBUG = false;
	public static final String TITLE = "2IcyBoiis";
	public static int HEIGHT, WIDTH;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		if(USEDEBUG){
			HEIGHT = 840;
			WIDTH = 560;
		}
		else{
			HEIGHT = Gdx.graphics.getHeight();
			WIDTH = Gdx.graphics.getWidth();
		}


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
