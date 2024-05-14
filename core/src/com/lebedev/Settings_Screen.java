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

public class Settings_Screen implements Screen {
    LeviathanGame game;
    private Stage stage;
    private Skin skin;
    private int res_width = 1280; // chosen resolution width
    private int res_height = 720; // chosen resolution height

    public Settings_Screen(LeviathanGame game){
        this.game = game;

        stage = new Stage(new ExtendViewport(res_width, res_height)); // For UI
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);

        PictureClass settings = new PictureClass();
        settings.get_assets("Menus/SettingsMenu.png",0,0,1280,720);
        stage.addActor(settings);

        stage.addActor(root);

        skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json")); //TODO: Temporary change to basic uiskin

        Table menuButtons = new Table();

        SelectBox selectBox = new SelectBox(skin);

        selectBox.setAlignment(Align.right);
        selectBox.getList().setAlignment(Align.right);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);

        selectBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println(selectBox.getSelected());
            }
        });
        selectBox.setItems("1280x720", "1280x800", "1920x1080","1920x1200", "2560x1440", "2560x1600", "3840x2160");
        selectBox.setSelected("1280x720");
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Process of converting a selected string into 2 int variables
                String select = selectBox.getSelection().toString();
                select = select.replaceAll("[{}]", "");
                int index = select.indexOf("x");
                String str1 = select.substring(0, index);
                String str2 = select.substring(index + 1);
                res_width = Integer.parseInt(str1); // chosen resolution
                res_height = Integer.parseInt(str2); // chosen resolution
            }
        });

        root.add(menuButtons).expandY().expandX().left();

        menuButtons.defaults().padLeft(80).spaceTop(20).width(470).height(100);

        menuButtons.row();
        Button toggle_button = new Button(skin,"toggle");
        menuButtons.add(toggle_button).padTop(200).colspan(2);
        Label label = new Label("FULLSCREEN",skin);
        toggle_button.add(label).expandX().left().padLeft(40);

        toggle_button.setChecked(LeviathanGame.fullscreen);

        menuButtons.row();

        TextButton apply_button = new TextButton("APPLY",skin);
        menuButtons.add(apply_button).width(190);
        apply_button.getLabel().setAlignment(Align.left);
        apply_button.getLabelCell().padLeft(40);

        menuButtons.add(selectBox).width(260).height(100).padLeft(20);

        menuButtons.row();
        TextButton return_button = new TextButton("RETURN",skin);
        menuButtons.add(return_button).colspan(2);
        return_button.getLabel().setAlignment(Align.left);
        return_button.getLabelCell().padLeft(40);

        if (LeviathanGame.fullscreen){
            apply_button.setDisabled(true);
            selectBox.setDisabled(true);
        }

        toggle_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (toggle_button.isChecked()) {
                    System.out.println("FullScreen");
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    LeviathanGame.fullscreen = true;
                    // Prohibition to change resolution while fullscreen
                    apply_button.setDisabled(true);
                    selectBox.setDisabled(true);
                } else {
                    System.out.println("Windowed");
                    Gdx.graphics.setWindowedMode(res_width,res_height);
                    LeviathanGame.fullscreen = false;
                    // Allowance to change resolution while not fullscreen
                    apply_button.setDisabled(false);
                    selectBox.setDisabled(false);
                }
            }
        });
        apply_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(res_width +"x"+ res_height);
                Gdx.graphics.setWindowedMode(res_width,res_height);
            }
        });
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("RETURN");
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
        game.batch.dispose();
        stage.dispose();
    }
}