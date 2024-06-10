package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import static com.lebedev.LeviathanGame.current_level;
import static com.lebedev.LeviathanGame.flag;

public class PauseScreen implements Screen {
    LeviathanGame game;
    private Skin skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
    private final ExtendViewport extendViewport;
    private Texture fader = new Texture(Gdx.files.internal("assets/Pictures/Menus/fader2.png"));
    private Texture pause = new Texture(Gdx.files.internal("assets/Pictures/Menus/Pause.png"));
    private Stage stage;
    private static GameScreen gameScreen;
    private static RouteMapScreen routeMapScreen;
    private static EventScreen eventScreen;
    private Batch batch = new SpriteBatch();
    private boolean oneTimeFade = true;
    public static int primeScreen;

    public PauseScreen(LeviathanGame game, GameScreen gameScreen , RouteMapScreen routeMapScreen,
                       EventScreen eventScreen, int primeScreen) { //primeScreen: 0 -none, 1-game, 2-map, 3-event
        this.game = game;
        this.gameScreen = gameScreen;
        this.routeMapScreen = routeMapScreen;
        this.eventScreen = eventScreen;
        this.primeScreen = primeScreen;

        extendViewport = new ExtendViewport(1280, 720+flag); // For background

        stage = new Stage(extendViewport);

        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer);

        PictureClass pause = new PictureClass();
        pause.get_assets("Menus/pause.png", 500, 200, 280,400);
        stage.addActor(pause);

        TextButton resumeButton = new TextButton("Resume",skin);
        resumeButton.getLabel().setAlignment(Align.center);
        resumeButton.setBounds(550,400,180,50);;
        stage.addActor(resumeButton);

        TextButton resetButton = new TextButton("Reset Game",skin);
        resetButton.getLabel().setAlignment(Align.center);
        resetButton.setBounds(550,330,180,50);;
        stage.addActor(resetButton);
        stage.setDebugAll(true);

        TextButton backButton = new TextButton("Main menu",skin);
        backButton.getLabel().setAlignment(Align.center);
        backButton.setBounds(550,260,180,50);;
        stage.addActor(backButton);
        stage.setDebugAll(true);

        switch (primeScreen) {
            case 1:
                resumeButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        LeviathanGame.inputMultiplexer.removeProcessor(stage);
                        game.setScreen(gameScreen);
                        gameScreen.updateUI();
                        gameScreen.resume();
                    }
                });

                resetButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        LeviathanGame.inputMultiplexer.removeProcessor(stage);
                        DeckGenerator.global_deck = new ArrayList<>();
                        Verg.HP = 40;
                        Verg.MAX_HP = 40;
                        Verg.MAX_ENERGY = 3;
                        LeviathanGame.started = false;

                        if (current_level != 1) {
                            BG_Music.startMusic("01_STS", true);
                            LeviathanGame.current_level = 1;
                        }

                        gameScreen.dispose();
                        game.setScreen(new MainMenuScreen(game));
                    }
                });
                break;
            case 2:
                resumeButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        LeviathanGame.inputMultiplexer.removeProcessor(stage);
                        game.setScreen(routeMapScreen);
                        routeMapScreen.updateUiBar();
                        routeMapScreen.resume();
                    }
                });
                resetButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        LeviathanGame.inputMultiplexer.removeProcessor(stage);
                        DeckGenerator.global_deck = new ArrayList<>();
                        Verg.HP = 40;
                        Verg.MAX_HP = 40;
                        Verg.MAX_ENERGY = 3;
                        LeviathanGame.started = false;

                        if (current_level != 1) {
                            BG_Music.startMusic("01_STS", true);
                            LeviathanGame.current_level = 1;
                        }
                        routeMapScreen.dispose();
                        game.setScreen(new MainMenuScreen(game));
                    }
                });

                break;
        }

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LeviathanGame.started = true;
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
    }
    //primeScreen: 0 -none, 1-game, 2-map, 3-event
    public static GameScreen getPrimeScreen1(){
        return gameScreen;
    }
    static RouteMapScreen getPrimeScreen2(){
        return routeMapScreen;
    }
    private static EventScreen getPrimeScreen3(){
        return eventScreen;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(fader, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
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
        LeviathanGame.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}
