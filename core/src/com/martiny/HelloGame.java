package com.martiny;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HelloGame extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
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
