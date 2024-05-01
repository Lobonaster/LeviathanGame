package com.martiny;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class HelloWorld extends Game {

	SpriteBatch batch;
	Texture img;
	Texture img2;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("assets/Pictures/badlogic.jpg");
		img2 = new Texture("assets/Pictures/verg.jpg");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(img2, 300,200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
