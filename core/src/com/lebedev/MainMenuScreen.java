package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class MainMenuScreen implements Screen {
    private static final int EXIT_W = 700;
    private static final int EXIT_H = 100;
    private static final int EXIT_Y = LeviathanGame.HEIGHT / 2 - 620 / 2 ;
    private static final int PLAY_W = 700;
    private static final int PLAY_H = 100;
    private static final int PLAY_Y = LeviathanGame.HEIGHT / 2 - 240 / 2;
    private static final int SETTINGS_W = 700;
    private static final int SETTINGS_H = 100;
    private static final int SETTINGS_Y = LeviathanGame.HEIGHT / 2 - 430 / 2;
    LeviathanGame game;
    Texture menu_bg;
    Texture exit_button;
    Texture exit_button_hover;
    Texture next_button;
    Texture next_button_hover;

    Texture settings_button;
    Texture settings_button_hover;
    public MainMenuScreen (LeviathanGame game) {
        this.game = game;
        menu_bg = new Texture("assets/Pictures/Menus/menu.png");
        next_button_hover = new Texture("assets/Pictures/Buttons/next_hover.png");
        exit_button_hover = new Texture("assets/Pictures/Buttons/exit_hover.png");
        next_button = new Texture("assets/Pictures/Buttons/next.png");
        exit_button = new Texture("assets/Pictures/Buttons/exit.png");
        settings_button = new Texture("assets/Pictures/Buttons/settings.png");
        settings_button_hover = new Texture("assets/Pictures/Buttons/settings_hover.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(menu_bg,0,0, LeviathanGame.WIDTH, LeviathanGame.HEIGHT);

        int x = LeviathanGame.WIDTH / 4 - PLAY_W / 2 ;

        if (Gdx.input.getX() < x + PLAY_W
                && Gdx.input.getX() > x
                && LeviathanGame.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_H
                && LeviathanGame.HEIGHT - Gdx.input.getY() > PLAY_Y)
        {
            game.batch.draw(next_button_hover,x,PLAY_Y,PLAY_W, PLAY_H);
            if (Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }
        else
        {
            game.batch.draw(next_button,x,PLAY_Y,PLAY_W, PLAY_H);
        }

        x = LeviathanGame.WIDTH / 4 - SETTINGS_W / 2 ;

        if (Gdx.input.getX() < x + SETTINGS_W
                && Gdx.input.getX() > x
                && LeviathanGame.HEIGHT - Gdx.input.getY() < SETTINGS_Y + SETTINGS_H
                && LeviathanGame.HEIGHT - Gdx.input.getY() > SETTINGS_Y)
        {
            game.batch.draw(settings_button_hover,x,SETTINGS_Y,SETTINGS_W, SETTINGS_H);
            if (Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }
        else
        {
            game.batch.draw(settings_button,x,SETTINGS_Y,SETTINGS_W, SETTINGS_H);
        }

        x = LeviathanGame.WIDTH / 4 - EXIT_W / 2;

        if (Gdx.input.getX() < x + EXIT_W
                && Gdx.input.getX() > x
                && LeviathanGame.HEIGHT - Gdx.input.getY() < EXIT_Y + EXIT_H
                && LeviathanGame.HEIGHT - Gdx.input.getY() > EXIT_Y)
        {
            game.batch.draw(exit_button_hover,x,EXIT_Y,EXIT_W, EXIT_H);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
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
