package com.martiny;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


public class MainMenuScreen implements Screen {
    private static final int EXIT_W = 100;
    private static final int EXIT_H = 100;
    private static final int EXIT_Y = HelloGame.HEIGHT / 2 - 420 / 2 ;
    private static final int PLAY_W = 100;
    private static final int PLAY_H = 100;
    private static final int PLAY_Y = HelloGame.HEIGHT / 2 - 80 / 2;
    private static final int SETTINGS_W = 100;
    private static final int SETTINGS_H = 100;
    private static final int SETTINGS_Y = HelloGame.HEIGHT / 2 - 250 / 2;
    HelloGame game;
    Texture menu_bg;
    Texture exit_button;
    Texture exit_button_hover;
    Texture next_button;
    Texture next_button_hover;

    Texture settings_button;
    Texture settings_button_hover;
    public MainMenuScreen (HelloGame game) {
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

        game.batch.draw(menu_bg,0,0,HelloGame.WIDTH,HelloGame.HEIGHT);

        int x = HelloGame.WIDTH / 2 - PLAY_W / 2 ;

        if (Gdx.input.getX() < x + PLAY_W
                && Gdx.input.getX() > x
                && HelloGame.HEIGHT - Gdx.input.getY() < PLAY_Y + PLAY_H
                && HelloGame.HEIGHT - Gdx.input.getY() > PLAY_Y)
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

        x = HelloGame.WIDTH / 2 - SETTINGS_W / 2 ;

        if (Gdx.input.getX() < x + SETTINGS_W
                && Gdx.input.getX() > x
                && HelloGame.HEIGHT - Gdx.input.getY() < SETTINGS_Y + SETTINGS_H
                && HelloGame.HEIGHT - Gdx.input.getY() > SETTINGS_Y)
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

        x = HelloGame.WIDTH / 2 - EXIT_W / 2;

        if (Gdx.input.getX() < x + EXIT_W
                && Gdx.input.getX() > x
                && HelloGame.HEIGHT - Gdx.input.getY() < EXIT_Y + EXIT_H
                && HelloGame.HEIGHT - Gdx.input.getY() > EXIT_Y)
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
