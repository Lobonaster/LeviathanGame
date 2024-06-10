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
import com.badlogic.gdx.utils.viewport.FillViewport;

import static com.lebedev.LeviathanGame.flag;

public class Settings_Screen implements Screen {
    LeviathanGame game;
    private Stage stage;
    private Skin skin;
    private Stage bg_stage;

    public Settings_Screen(LeviathanGame game){
        this.game = game;

        bg_stage = new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        PictureClass mainMenu = new PictureClass();
        mainMenu.get_assets("Menus/Main/LeviathanMenuBG.png",0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        bg_stage.addActor(mainMenu);

        stage = new Stage(new ExtendViewport(1280,720+flag)); // For UI

        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer);

        Table root = new Table();
        root.setFillParent(true);

        PictureClass settings2 = new PictureClass();
        settings2.get_assets("Menus/Settings/SettingsLOGO.png",80,474+flag,474,108);
        stage.addActor(settings2);

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
        selectBox.setSelected(LeviathanGame.res_choice);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Process of converting a selected string into 2 int variables
                String select = selectBox.getSelection().toString();
                select = select.replaceAll("[{}]", "");
                LeviathanGame.res_choice = select;
                int index = select.indexOf("x");
                String str1 = select.substring(0, index);
                String str2 = select.substring(index + 1);
                LeviathanGame.res_width = Integer.parseInt(str1); // chosen resolution
                LeviathanGame.res_height = Integer.parseInt(str2); // chosen resolution
            }
        });

        root.add(menuButtons).expandY().expandX().left();


        Label remainingLabel = new Label("BGM Volume: " +String.valueOf(LeviathanGame.volumePercent), skin);
        remainingLabel.setBounds(0,0,50,30);

        Slider volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setVisualPercent((float) LeviathanGame.volumePercent /100);
        menuButtons.add(remainingLabel).padTop(200).padLeft(40);
        menuButtons.add(volumeSlider).padTop(200).padLeft(0).width(300).height(30);

        menuButtons.defaults().padLeft(90).spaceTop(20).width(450).height(80);

        menuButtons.row();
        Button toggle_button = new Button(skin,"toggle");
        menuButtons.add(toggle_button).colspan(2);
        Label label = new Label("FULLSCREEN",skin);
        toggle_button.add(label).expandX().left().padLeft(40);

        toggle_button.setChecked(LeviathanGame.fullscreen);

        menuButtons.row();

        TextButton apply_button = new TextButton("APPLY",skin);
        menuButtons.add(apply_button).width(170);
        apply_button.getLabel().setAlignment(Align.left);
        apply_button.getLabelCell().padLeft(40);

        menuButtons.add(selectBox).width(260).height(80).padLeft(20);

        menuButtons.row();
        TextButton return_button = new TextButton("RETURN",skin);
        menuButtons.add(return_button).colspan(2);
        return_button.getLabel().setAlignment(Align.left);
        return_button.getLabelCell().padLeft(40);

        if (LeviathanGame.fullscreen){
            apply_button.setDisabled(true);
            selectBox.setDisabled(true);
        }

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float volume = volumeSlider.getValue();
                BG_Music.volumeChange(volume);
                LeviathanGame.volumePercent = (int) (volume * 100);
                remainingLabel.setText("BGM Volume: "+String.valueOf(LeviathanGame.volumePercent));
            }
        });

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
                    if (Gdx.graphics.getHeight()%100 == 0){
                        flag = 40;
                    }else {
                        flag = 0;
                    }
                } else {
                    System.out.println("Windowed");
                    Gdx.graphics.setWindowedMode(LeviathanGame.res_width,LeviathanGame.res_height);
                    LeviathanGame.fullscreen = false;
                    // Allowance to change resolution while not fullscreen
                    apply_button.setDisabled(false);
                    selectBox.setDisabled(false);
                    if (Gdx.graphics.getHeight()%100 == 0){
                        flag = 40;
                    }else {
                        flag = 0;
                    }
                }
            }
        });
        apply_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(LeviathanGame.res_width +"x"+ LeviathanGame.res_height);
                Gdx.graphics.setWindowedMode(LeviathanGame.res_width,LeviathanGame.res_height);
                if (Gdx.graphics.getHeight()%100 == 0){
                    flag = 40;
                    dispose();
                    game.setScreen(new Settings_Screen(game));
                }else {
                    flag = 0;
                }
            }
        });
        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("RETURN");
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        stage.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        bg_stage.act();
        bg_stage.draw();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        bg_stage.getViewport().update(width, height,true);
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
        LeviathanGame.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}