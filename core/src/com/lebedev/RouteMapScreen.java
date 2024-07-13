package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


import java.util.Random;


import static com.lebedev.LeviathanGame.current_level;
import static com.lebedev.LeviathanGame.flag;

public class RouteMapScreen implements Screen {

    private LeviathanGame game;
    private Stage stage;
    private Table pathsTable;
    private boolean[][] pathCompletion;
    private int activePath = -1;
    private ExtendViewport extendViewport = new ExtendViewport(1280,720+flag);
    private final Skin skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
    private Label hpLabel = new Label("",skin);
    private Label remainingLabel = new Label("",skin);
    TextButton return_button = new TextButton("Pause",skin);
    private PictureClass ui_bar = new PictureClass();
    PictureClass bg = new PictureClass();

    TextButton boss_button;

    private static EventScreen eventScreenPH;
    private static GameScreen gameScreenPH;

    public RouteMapScreen(LeviathanGame game) {
        this.game = game;
        this.stage = new Stage(extendViewport);

        System.out.println("Creating a new stage");
        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer); // to regain focus


        bg.get_assets("BGS/stage1.png",0,0,1280,640+flag*2);
        stage.addActor(bg);

        PictureClass background = new PictureClass();
        background.get_assets("BGS/map_background.png",390,0,500,1500);
        stage.addActor(background);


        ui_bar.get_assets("Menus/Ui_Bar.png", 0, (int) extendViewport.getMinWorldHeight()-80+ flag, 1280,80);

        pathCompletion = new boolean[3][14]; // 3 paths, each with 14 steps
        initializePaths();
        stage.addActor(ui_bar);
        ui_bar.setZIndex(3);
        hpLabel = new Label("HP: "+Verg.HP+" / "+Verg.MAX_HP, skin);
        hpLabel.setAlignment(Align.center);
        hpLabel.setWrap(false);
        hpLabel.setFontScale(1.5f);
        hpLabel.setBounds(100,650+flag*2,180,60);
        stage.addActor(hpLabel);

        int result =  DeckGenerator.getdecksize();
        remainingLabel = new Label("Deck: "+result+" Cards", skin); //TODO: FIX THIS

        remainingLabel.setAlignment(Align.center);
        remainingLabel.setWrap(false);
        remainingLabel.setFontScale(1.5f);
        remainingLabel.setBounds(300,650+flag*2,180,60);
        stage.addActor(remainingLabel);

        return_button.setPosition(1205,(int) extendViewport.getMinWorldHeight()-65+ flag);
        return_button.setSize(60, 50);
        stage.addActor(return_button);

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("pause");
                LeviathanGame.inputMultiplexer.removeProcessor(stage);

                ui_bar.remove();

                game.setScreen(new PauseScreen(game,gameScreenPH,RouteMapScreen.this,eventScreenPH,2));
                updateUiBar();
            }
        });

        //stage.setDebugAll(true);
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
                final int random_button_type;
                if(j>0&& j<13) {
                    final Random buttonRandom = new Random();
                    random_button_type = buttonRandom.nextInt(101);
                } else if (j == 13) {
                    random_button_type = 99; //last room always event
                } else {
                    random_button_type = 0; //first room always enemy
                }

                TextButton button = ButtonMaker(random_button_type,pathIndex,buttonIndex);

                if (j > 0) {
                    button.setDisabled(true);
                }
                changeColor(button,false);
                pathTable.add(button).width(60).height(50).padTop(23).padRight(30).padLeft(30).row();
            }

            if (i == 1){
                final int pathIndex = i;
                final int buttonIndex = 14;
                boss_button = new TextButton("Boss", skin);
                boss_button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = Verg.MAX_ENERGY;

                        makeEnemies("boss");
                        LeviathanGame.boss_battle = true;

                        activePath = pathIndex;
                        game.setScreen(new GameScreen(game, RouteMapScreen.this, activePath, 14));
                    }
                });
                boss_button.setDisabled(true);
                changeColor(boss_button,false);
                pathTable.add(boss_button).width(80).height(60).padTop(23).padRight(20).padLeft(20).row();
            }
            pathsTable.add(pathTable).top().padRight(30).padLeft(30).padBottom(400);
        }
        ScrollPane scrollPane = new ScrollPane(pathsTable);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    public void updatePathProgress(int pathIndex, int buttonIndex) {
        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer); // to regain focus

        hpLabel.setText("HP: "+Verg.HP+" / "+Verg.MAX_HP);
        int result = 10 + DeckGenerator.global_deck.size();
        remainingLabel.setText("Deck: "+result+" Cards");
        pathCompletion[pathIndex][buttonIndex] = true;
        if (buttonIndex < pathCompletion[pathIndex].length - 1) {
            TextButton previousButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex);
            previousButton.setDisabled(true);
            changeColor(previousButton, true);
            TextButton nextButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex + 1);
            nextButton.setDisabled(false);
            changeColor(nextButton, false);
        } else {
            TextButton previousButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(13);
            previousButton.setDisabled(true);
            changeColor(previousButton, true);
            boss_button.setDisabled(false);
            changeColor(boss_button, false);
        }
    }

    public void BlockStarterButtons(int activePath, int pathIndex, int buttonIndex) {
        for (pathIndex = 0; pathIndex < 3; pathIndex++) {
            if (pathIndex != activePath) {
                TextButton startButton = (TextButton) ((Table) pathsTable.getCells().get(pathIndex).getActor()).getChildren().get(buttonIndex);
                startButton.setDisabled(true);
                changeColor(startButton, true);
            }
        }
    }
    private TextButton ButtonMaker(int random_button_type,int pathIndex, int buttonIndex){
        TextButton button = new TextButton("placeholder", skin);
        int button_type = -1;
        if (random_button_type < 60) { // 60% for enemy
            button_type = 0;
        } else if (59 < random_button_type && random_button_type< 81) { //20% For Elite
            button_type = 1;
        } else { //20% For Event
            button_type = 2;
        }
        switch (button_type){
            case 0:
                button = new TextButton("Enemy", skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = Verg.MAX_ENERGY;

                        makeEnemies("regular");

                        activePath = pathIndex;
                        BlockStarterButtons(activePath,pathIndex,buttonIndex);
                        game.setScreen(new GameScreen(game, RouteMapScreen.this, activePath, buttonIndex));
                    }
                });
                break;
            case 1:
                button = new TextButton("Elite", skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = Verg.MAX_ENERGY;

                        makeEnemies("elite");

                        activePath = pathIndex;
                        BlockStarterButtons(activePath,pathIndex,buttonIndex);
                        game.setScreen(new GameScreen(game, RouteMapScreen.this, activePath, buttonIndex));
                    }
                });
                break;
            case 2:
                button = new TextButton("Event", skin);
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Verg.ENERGY = Verg.MAX_ENERGY;

                        activePath = pathIndex;
                        BlockStarterButtons(activePath,pathIndex,buttonIndex);
                        game.setScreen(new EventScreen(game, RouteMapScreen.this, activePath, buttonIndex));
                    }
                });
                break;
        }
        return button;
    }

    private void makeEnemies(String enemy_type){
        EnemyMaker.enemyMaker(enemy_type);
    };
    private void changeColor(TextButton button, Boolean completed){
        String btn_name = button.getText().toString();
        switch (btn_name) {
            case "Enemy":
                button.getLabel().setColor(Color.GRAY);
                button.setColor(Color.WHITE);
                break;
            case "Elite":
                button.getLabel().setColor(Color.ORANGE);
                button.setColor(Color.WHITE);
                break;
            case "Event":
                button.getLabel().setColor(Color.GREEN);
                button.setColor(Color.WHITE);
                break;
            case "Boss":
                button.getLabel().setColor(Color.RED);
                button.setColor(Color.WHITE);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + btn_name);
        }
        if (button.isDisabled()){
            if (completed){
                button.getLabel().setColor(Color.LIGHT_GRAY);
                button.setColor(Color.GRAY);
            }
        }else {
            button.getLabel().setColor(Color.RED);
            button.setColor(Color.SLATE);
        }
    }

    public void updateUiBar(){
        ui_bar.remove();
        ui_bar.get_assets("Menus/Ui_Bar.png", 0, (int) extendViewport.getMinWorldHeight()-80+ flag, 1280,80);
        stage.addActor(ui_bar);
        ui_bar.setZIndex(3);
        extendViewport = new ExtendViewport(1280,720+flag);
        remainingLabel.setBounds(300,650+flag*2,180,60);
        hpLabel.setBounds(100,650+flag*2,180,60);
        return_button.setPosition(1205,(int) extendViewport.getMinWorldHeight()-65+ flag);
    }
    public void updateBG(){
        //bg.remove();;
        //bg.get_assets("BGS/stage1.png",0,0,1280,640+flag*2);
        //stage.addActor(bg);
        //bg.setZIndex(0);
        //extendViewport = new ExtendViewport(1280,720+flag);
    }

    @Override
    public void show() {
        // nothing to do here
    }

    @Override
    public void render(float delta) {

        stage.draw();
        //stage.getBatch().disableBlending(); // only solution to stop ui disappear
        stage.act(delta);

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
        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer);
    }

    @Override
    public void hide() {
        LeviathanGame.inputMultiplexer.removeProcessor(stage);
    }

    @Override
    public void dispose() {
        LeviathanGame.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
        skin.dispose();
    }
}
