package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.lebedev.VergVisuals.*;

public class GameScreen implements Screen {
    // TODO: 14.05.2024 needs many adjustments
    public static final float SPEED = 440; // TODO: DELETE THIS
    public static final float ROLL_SWITCH_TIME = 0.15f; // unused for now
    LeviathanGame game;
    private Skin skin;
    private ExtendViewport extendViewport;
    private Stage stage;

    VergVisuals vergActor = new VergVisuals();

    public GameScreen(LeviathanGame game){
        this.game = game;

        extendViewport = new ExtendViewport(1280, 720); // For background
        game.batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json")); //TODO: Temporary change to basic uiskin

        stage = new Stage(new ExtendViewport(1280, 720)); // For UI
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        PictureClass bg = new PictureClass();
        bg.get_assets("BGS/stage1.png",0,0,1280,720);
        stage.addActor(bg);

        PictureClass ui_bar = new PictureClass();
        ui_bar.get_assets("Menus/Ui_Bar.png",0,650,1280,70);
        stage.addActor(ui_bar);

        vergActor.setPos(230,170);
        stage.addActor(vergActor);

        stage.addActor(root);

        Table menuButtons = new Table();
        root.add(menuButtons).expandY().expandX().right().top();

        menuButtons.defaults().padRight(10).padTop(10).width(50).height(50);

        TextButton return_button = new TextButton("ESC",skin);
        menuButtons.add(return_button);

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("ESC");
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.DARK_GRAY);

        vergActor.getDelta(delta);

        //extendViewport.apply();

        //game.batch.setProjectionMatrix(extendViewport.getCamera().combined);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        extendViewport.update(width,height);
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
        game.batch.dispose();
        stage.dispose();
    }
}
