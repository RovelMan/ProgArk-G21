package com.icy.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icy.game.Controller.Connection;
import com.icy.game.Views.MenuScreen;
import io.socket.client.Socket;
import io.socket.client.IO;

public class IcyGame extends Game {

	//Change this to "true" to get same screen-size as an mobile device
	public static final boolean USEDEBUG = true;
	public static final String TITLE = "2IcyBoiis";
	public static final int DEBUGHEIGHT = 840, DEBUGWIDTH = 560, FPS = 60;
	public static int HEIGHT, WIDTH;
	public SpriteBatch batch;

	public Connection connection;
	
	@Override
	public void create () {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
		//connection = new Connection();
		//Socket socket = connection.getSocket();
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
