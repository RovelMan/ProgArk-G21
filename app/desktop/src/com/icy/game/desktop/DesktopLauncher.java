package com.icy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icy.game.IcyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = IcyGame.TITLE;
		config.height = IcyGame.HEIGHT;
		config.width = IcyGame.WIDTH;
		config.foregroundFPS = 60;
		new LwjglApplication(new IcyGame(), config);
	}
}
