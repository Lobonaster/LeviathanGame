package com.martiny;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(HelloGame.WIDTH, HelloGame.HEIGHT);
		config.setResizable(false);
		config.setForegroundFPS(60);
		config.setTitle("testing...");
		new Lwjgl3Application(new HelloGame(), config);
	}
}
