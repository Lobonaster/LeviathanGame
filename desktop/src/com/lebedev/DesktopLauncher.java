package com.lebedev;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(LeviathanGame.WIDTH, LeviathanGame.HEIGHT);
		config.setResizable(false);
		config.setForegroundFPS(120);
		config.setTitle("testing...");
		new Lwjgl3Application(new LeviathanGame(), config);
	}
}
