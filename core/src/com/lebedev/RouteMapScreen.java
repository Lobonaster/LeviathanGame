package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RouteMapScreen implements Screen {

    private LeviathanGame game;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private PictureClass background;
    private Table pathsTable;
    private boolean[][] pathCompletion;
    private int activePath = -1;

    public RouteMapScreen(LeviathanGame game) {
        this.game = game;
        this.stage = new Stage(new ExtendViewport(1280, 720));
        this.skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("assets/Pictures/BGS/map_background.png"));
        PictureClass background = new PictureClass();
        background.get_assets("BGS/map_background.png",390,0,500,1500);
        stage.addActor(background);

        pathCompletion = new boolean[3][14]; // 3 paths, each with 14 steps
        initializePaths();
        stage.setDebugAll(true);
    }

    private void initializePaths() {
        pathsTable = new Table();
        pathsTable.setFillParent(true);

        for (int i = 0; i < 3; i++) {
            Table pathTable = new Table();
            pathTable.center();
            for (int j = 0; j < 14; j++) {
                final int pathIndex = i;
                final int buttonIndex = j;
                TextButton button = new TextButton("Step " + (j + 1), skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = 3;
                        enemyTest.HP = 50;
                        enemyTest.pattern = 1;

                        activePath = pathIndex;
                        game.setScreen(new GameScreen(game, RouteMapScreen.this, activePath, buttonIndex));
                    }
                });
                if (j > 0) {
                    button.setDisabled(true);
                }
                pathTable.add(button).padTop(23).pad(15).row();
            }
            pathsTable.add(pathTable).center().pad(10);
        }

        ScrollPane scrollPane = new ScrollPane(pathsTable);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    public void updatePathProgress(int pathIndex, int buttonIndex) {
        if (pathIndex < 0 || pathIndex >= pathCompletion.length || buttonIndex < 0 || buttonIndex >= pathCompletion[pathIndex].length) {
            boolean started = true;
            return;
        }
        pathCompletion[pathIndex][buttonIndex] = true;
        if (buttonIndex < pathCompletion[pathIndex].length - 1) {
            TextButton nextButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex + 1);
            nextButton.setDisabled(false);
        }
        Gdx.input.setInputProcessor(stage); // to regain focus
    }

    @Override
    public void show() {
        // nothing to do here
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // nothing to do here
    }

    @Override
    public void resume() {
        // nothing to do here
    }

    @Override
    public void hide() {
        // nothing to do here
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
