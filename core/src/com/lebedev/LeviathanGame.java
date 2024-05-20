package com.lebedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class LeviathanGame extends Game {
	public SpriteBatch batch;
	public static boolean fullscreen = false;
	public static int flag = 0; //for 16:10 interactions
	public static String res_choice = "1280x720";
	public static int res_width = 1280; // chosen resolution width
	public static int res_height = 720; // chosen resolution height

	@Override
	public void create () {
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {

	}
}
