package com.lebedev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class LeviathanGame extends Game {
	public SpriteBatch batch;
	public static boolean fullscreen = false;
	public static int flag = 0; //for 16:10 interactions
	public static String res_choice = "1280x720";
	public static int res_width = 1280; // chosen resolution width
	public static int res_height = 720; // chosen resolution height
	public static int volumePercent = 30;

	public static ArrayList<CardClass> global_deck = new ArrayList<>();
	public static int current_level = 1;
	public static boolean boss_battle = false;
	public static boolean started = false;

	public static InputMultiplexer inputMultiplexer;
	static Music music;

	@Override
	public void create () {
		BG_Music.startMusic("01_STS",false);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Gdx.audio.newSound(Gdx.files.internal("assets/Audio/click.mp3")).play();
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		});
		Gdx.input.setInputProcessor(inputMultiplexer);
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
