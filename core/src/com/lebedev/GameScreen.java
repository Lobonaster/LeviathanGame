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
    // TODO: 12.05.2024 needs a full remake
    public static final float SPEED = 440;
    public static final float ROLL_SWITCH_TIME = 0.15f;
    float stateTime;
    LeviathanGame game;
    private Skin skin;
    private Texture stageBG;
    private ExtendViewport extendViewport;
    private Stage stage;

    public GameScreen(LeviathanGame game){
        this.game = game;

        extendViewport = new ExtendViewport(1280, 720); // For background
        game.batch = new SpriteBatch();

        stageBG = new Texture("assets/Pictures/BGS/stage1.png");
        skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json")); //TODO: Temporary change to basic uiskin

        stage = new Stage(new ExtendViewport(1280, 720)); // For UI
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Table menuButtons = new Table();

        root.add(menuButtons).expandY().expandX().right().top();

        menuButtons.defaults().padRight(40).padTop(40).width(100).height(100);

        TextButton return_button = new TextButton("RETURN",skin);
        menuButtons.add(return_button);
        //return_button.getLabel().setAlignment(Align.left);
        //return_button.getLabelCell();

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("RETURN");
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.setDebugAll(true);

        VergVisuals verg = new VergVisuals();
        verg.init();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        ScreenUtils.clear(Color.DARK_GRAY);
        extendViewport.apply();
        game.batch.setProjectionMatrix(extendViewport.getCamera().combined);
        game.batch.begin();

        game.batch.draw(stageBG,-640,-360,1280,720);

        game.batch.draw((TextureRegion) rolls[VergVisuals.roll].getKeyFrame(stateTime, true), verg_x, verg_y, VergVisuals.VERG_WIDTH, VERG_HEIGHT);

        game.batch.end();

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
