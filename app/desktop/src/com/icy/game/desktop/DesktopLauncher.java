package com.icy.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icy.game.IcyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = IcyGame.TITLE;
		if (IcyGame.USEDEBUG) {
			config.height = IcyGame.DEBUGHEIGHT;
			config.width = IcyGame.DEBUGWIDTH;
		} else {
			config.height = IcyGame.HEIGHT;
			config.width = IcyGame.WIDTH;
		}
		config.foregroundFPS = IcyGame.FPS;
		new LwjglApplication(new IcyGame(), config);
	}
}
