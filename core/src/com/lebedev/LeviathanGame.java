package com.lebedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LeviathanGame extends Game {
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
