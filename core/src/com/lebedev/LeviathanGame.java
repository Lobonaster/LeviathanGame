package com.lebedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class LeviathanGame extends Game {
	public SpriteBatch batch;
	public static int ScreenState = 0;

	public static int WIDTH = 1;
	public static int HEIGHT = 1;

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
