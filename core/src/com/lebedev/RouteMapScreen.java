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

import static com.lebedev.LeviathanGame.flag;

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
        this.stage = new Stage(new ExtendViewport(1280, 720+flag));
        this.skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("assets/Pictures/BGS/map_background.png"));
        PictureClass background = new PictureClass();
        background.get_assets("BGS/map_background.png",390,0,500,1500);
        stage.addActor(background);

        pathCompletion = new boolean[3][14]; // 3 paths, each with 14 steps
        initializePaths();

        TextButton return_button = new TextButton("ESC",skin);
        return_button.setPosition(1215,655);
        return_button.setWidth(50);
        return_button.setHeight(50);
        stage.addActor(return_button);

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("ESC");
                game.setScreen(new MainMenuScreen(game));
            }
        });

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
                TextButton button = new TextButton("stage " + (j + 1), skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = 3;
                        enemyTest.HP = 50;
                        enemyTest.dead = false;

                        activePath = pathIndex;
                        BlockStarterButtons(activePath,pathIndex,buttonIndex);
                        game.setScreen(new GameScreen(game, RouteMapScreen.this, activePath, buttonIndex));
                    }
                });
                if (j > 0) {
                    button.setDisabled(true);
                }
                pathTable.add(button).width(60).height(50).padTop(23).padRight(30).padLeft(30).row();
            }
            pathsTable.add(pathTable).top().padRight(30).padLeft(30).padBottom(400);
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
            TextButton previousButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex);
            previousButton.setDisabled(true);
            TextButton nextButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex + 1);
            nextButton.setDisabled(false);
        }
        Gdx.input.setInputProcessor(stage); // to regain focus
    }

    public void BlockStarterButtons(int activePath, int pathIndex, int buttonIndex) {
        for (pathIndex = 0; pathIndex < 3; pathIndex++) {
            if (pathIndex != activePath) {
                TextButton startButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex);
                startButton.setDisabled(true);
            }
        }
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
