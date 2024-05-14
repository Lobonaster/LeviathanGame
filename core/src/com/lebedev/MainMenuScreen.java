package com.lebedev;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MainMenuScreen implements Screen {
    LeviathanGame game;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(LeviathanGame game){
        this.game = game;

        stage = new Stage(new ExtendViewport(1280, 720));
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        PictureClass mainMenu = new PictureClass();
        mainMenu.get_assets("Menus/MainMenu.png",0,0,1280,720);
        stage.addActor(mainMenu);

        stage.addActor(root);
        skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json")); //TODO: Temporary change to basic uiskin

        Table menuButtons = new Table();
        root.add(menuButtons).expandY().expandX().left();

        menuButtons.defaults().padLeft(80).spaceTop(20).width(470).height(100);
        TextButton text_button1 = new TextButton("PLAY",skin);
        menuButtons.add(text_button1).padTop(200);
        text_button1.getLabel().setAlignment(Align.left);
        text_button1.getLabelCell().padLeft(40);

        menuButtons.row();
        TextButton text_button2 = new TextButton("SETTINGS",skin);
        menuButtons.add(text_button2);
        text_button2.getLabel().setAlignment(Align.left);
        text_button2.getLabelCell().padLeft(40);

        menuButtons.row();
        TextButton text_button3 = new TextButton("EXIT",skin);
        menuButtons.add(text_button3);
        text_button3.getLabel().setAlignment(Align.left);
        text_button3.getLabelCell().padLeft(40);


        text_button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("MMS CLICKED 1");
                game.setScreen(new GameScreen(game));
            }
        });
        text_button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("MMS CLICKED 2");
                game.setScreen(new Settings_Screen(game));
            }
        });
        text_button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("MMS CLICKED 3 AND EXIT");
                Gdx.app.exit();
            }
        });

        stage.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(0x0f140bf7));

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}