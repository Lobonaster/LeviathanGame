package com.lebedev;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.lebedev.LeviathanGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		config.setForegroundFPS(120);
		config.setTitle("LeviathanGameProject");
		new Lwjgl3Application(new LeviathanGame(), config);
	}
}
