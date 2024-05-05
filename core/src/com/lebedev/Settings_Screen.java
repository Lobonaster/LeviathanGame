package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Settings_Screen implements Screen {
    LeviathanGame game;
    private static final int EXIT_W = 700;
    private static final int EXIT_H = 100;
    private static final int EXIT_Y = LeviathanGame.HEIGHT / 2 - 620 / 2 ;
    Texture exit_button;
    Texture exit_button_hover;

    public Settings_Screen (LeviathanGame game) {
        this.game = game;
        exit_button_hover = new Texture("assets/Pictures/Buttons/exit_hover.png");
        exit_button = new Texture("assets/Pictures/Buttons/exit.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        int x = LeviathanGame.WIDTH / 2 - EXIT_W / 2;

        if (Gdx.input.getX() < x + EXIT_W
                && Gdx.input.getX() > x
                && LeviathanGame.HEIGHT - Gdx.input.getY() < EXIT_Y + EXIT_H
                && LeviathanGame.HEIGHT - Gdx.input.getY() > EXIT_Y)
        {
            game.batch.draw(exit_button_hover,x,EXIT_Y,EXIT_W, EXIT_H);
            if(Gdx.input.isTouched()){
                //TODO: ПРИДУМАЙ ЧТО НИБУДЬ!
                this.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
        else
        {
            game.batch.draw(exit_button,x,EXIT_Y,EXIT_W, EXIT_H);
        }
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
