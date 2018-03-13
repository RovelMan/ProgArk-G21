package com.icy.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Views.MenuScreen;

public class IcyGame extends Game {
	public SpriteBatch batch;
	public Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 1, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.begin();
		//batch.draw(img, 100, 100);
		//batch.end();

		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
