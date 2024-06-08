package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.lebedev.LeviathanGame.flag;

public class EventScreen implements Screen {

    LeviathanGame game;
    private Skin skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
    private ExtendViewport extendViewport;
    private Stage stage;
    private RouteMapScreen routeMapScreen;
    private int pathIndex;
    private int buttonIndex;

    public EventScreen(LeviathanGame game,RouteMapScreen routeMapScreen, int pathIndex, int buttonIndex){
        this.game = game;
        this.routeMapScreen = routeMapScreen;
        this.pathIndex = pathIndex;
        this.buttonIndex = buttonIndex;

        extendViewport = new ExtendViewport(1280, 720+flag);

        stage = new Stage(extendViewport);
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        PictureClass pic = new PictureClass();
        pic.get_assets("Buttons/exit.png", 100, (int) extendViewport.getMinWorldHeight()/2-80+LeviathanGame.flag*2, 290,180);
        stage.addActor(pic);

        PictureClass ui_bar = new PictureClass();
        ui_bar.get_assets("Menus/Ui_Bar.png", 0, (int) extendViewport.getMinWorldHeight()-80+LeviathanGame.flag, 1280,80);
        stage.addActor(ui_bar);

        TextButton return_button = new TextButton("ESC",skin);
        return_button.setPosition(1215,(int) extendViewport.getMinWorldHeight()-65+ flag);
        return_button.setSize(50, 50);
        stage.addActor(return_button);

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("ESC");
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table menuButtons = new Table();

        root.add(menuButtons).expandY().expandX().right();

        menuButtons.defaults().padRight(70).spaceTop(20).width(450).height(100);

        menuButtons.row();

        TextButton choice1 = new TextButton("Choice 1",skin);
        menuButtons.add(choice1);
        choice1.getLabel().setAlignment(Align.left);
        choice1.getLabelCell().padRight(40);

        //menuButtons.add().width(260).height(100).padLeft(20);

        menuButtons.row();
        TextButton choice2 = new TextButton("Choice 2",skin);
        menuButtons.add(choice2);
        choice2.getLabel().setAlignment(Align.left);
        choice2.getLabelCell().padRight(40);

        stage.addActor(root);
        stage.setDebugAll(true);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        stage.draw();
        stage.act(delta);
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
