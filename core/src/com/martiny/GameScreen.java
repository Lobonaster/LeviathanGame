package com.martiny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    public static  final float SPEED = 140;
    Texture img;
    Texture img2;
    float x, y;

    HelloWorld game;
    public GameScreen(HelloWorld game){
        this.game = game;
    }

    @Override
    public void show() {
        img = new Texture("assets/Pictures/badlogic.jpg");
        img2 = new Texture("assets/Pictures/verg.jpg");
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }

        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        game.batch.draw(img, 300,200);
        game.batch.draw(img2, x, y);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
