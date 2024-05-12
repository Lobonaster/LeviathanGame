package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.ui.*;


public class Settings_Screen implements Screen {
    // TODO: 12.05.2024 needs a full remake
    LeviathanGame game;
    private static final int EXIT_W = 100;
    private static final int EXIT_H = 100;
    private static final int EXIT_Y = LeviathanGame.HEIGHT / 2  - 100 / 2;
    private static final int SETTINGS_W = 700;
    private static final int SETTINGS_H = 100;
    private static final int SETTINGS_Y = LeviathanGame.HEIGHT / 2 + 200 / 2  ;
    private Skin skin;
    private CheckBox fullscreenCheckbox;
    private SelectBox<String> resolutionSelectBox;
    Texture exit_button;
    Texture exit_button_hover;
    Texture settings_button;
    Texture settings_button_hover;
    Button play_btn;


    public Settings_Screen (LeviathanGame game) {
        this.game = game;
        exit_button_hover = new Texture("assets/Pictures/Buttons/exit_hover.png");
        exit_button = new Texture("assets/Pictures/Buttons/exit.png");
        settings_button = new Texture("assets/Pictures/Buttons/settings.png");
        settings_button_hover = new Texture("assets/Pictures/Buttons/settings_hover.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
/**
public class Settings_Screen implements Screen {
    private final LeviathanGame game;
    private Skin skin;
    private CheckBox fullscreenCheckbox;
    private SelectBox<String> resolutionSelectBox;

    public Settings_Screen(LeviathanGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        fullscreenCheckbox = new CheckBox("Fullscreen", skin);
        fullscreenCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setFullscreen(((CheckBox) actor).isChecked());
            }
        });

        String[] resolutions = {"1400x720", "1920x1080"};
        resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.setItems(resolutions);
        resolutionSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SelectBox<String> selectBox = (SelectBox<String>) actor;
                String resolution = selectBox.getSelected();
                if (resolution.equals("1400x720")) {
                    game.setResolution(1400, 720);
                } else if (resolution.equals("1920x1080")) {
                    game.setResolution(1920, 1080);
                }
            }
        });

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (fullscreenCheckbox.isVisible()) {
                    fullscreenCheckbox.setVisible(false);
                    resolutionSelectBox.setVisible(true);
                } else {
                    fullscreenCheckbox.setVisible(true);
                    resolutionSelectBox.setVisible(false);
                }
                return true;
            }
        });

        fullscreenCheckbox.setPosition(10, 10);
        fullscreenCheckbox.setVisible(true);
        resolutionSelectBox.setPosition(10, 50);
        resolutionSelectBox.setVisible(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        fullscreenCheckbox.draw(game.batch, 1);
        resolutionSelectBox.draw(game.batch, 1);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / height;
        game.camera.viewportWidth = 1080 * aspectRatio;
        game.camera.viewportHeight = 1080;
        game.camera.position.set(game.camera.viewportWidth / 2, game.camera.viewportHeight / 2, 0);
        game.camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        skin.dispose();
    }

    @Override
    public void dispose() {
    }
} */
