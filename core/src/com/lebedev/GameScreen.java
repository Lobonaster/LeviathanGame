package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Objects;

public class GameScreen implements Screen {

    LeviathanGame game;
    private Skin skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
    private final ExtendViewport extendViewport;
    private Stage stage;
    Verg vergActor = new Verg();
    private final Table handTable;
    private Label remainingLabel = new Label("",skin);
    private Label discardedLabel = new Label("",skin);
    private Label energyLabel = new Label("",skin);
    private Label hpLabel = new Label("",skin);
    private DeckGenerator deckGenerator;
    private RouteMapScreen routeMapScreen;
    private final int pathIndex;
    private final int buttonIndex;
    enemyTest enemyActor = new enemyTest();

    public GameScreen(LeviathanGame game,RouteMapScreen routeMapScreen, int pathIndex, int buttonIndex){
        this.game = game;
        this.routeMapScreen = routeMapScreen;
        this.pathIndex = pathIndex;
        this.buttonIndex = buttonIndex;

        extendViewport = new ExtendViewport(1280, 720); // For background

        stage = new Stage(extendViewport);

        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer);

        if (LeviathanGame.current_level == 1 && LeviathanGame.boss_battle){
            BG_Music.startMusic("09_STS",true);
        } else if (LeviathanGame.current_level == 2 && LeviathanGame.boss_battle){
            BG_Music.startMusic("23_STS",true);
        }

        PictureClass stg1 = new PictureClass();
        stg1.get_assets("BGS/stage1.png",0,0,1280,720);
        stage.addActor(stg1);

        PictureClass ui_bar = new PictureClass();
        ui_bar.get_assets("Menus/Ui_Bar.png", 0, (int) extendViewport.getMinWorldHeight()-80+LeviathanGame.flag*2, 1290,80);
        stage.addActor(ui_bar);

        stage.addActor(vergActor);


        stage.addActor(enemyActor);

        deckGenerator = new DeckGenerator( this ,vergActor,enemyActor);

        handTable = new Table();
        handTable.bottom().padBottom(-90);
        handTable.defaults().width(180).height(230).pad(-30); // Cards pads and sizes fixed

        ScrollPane scrollPane = new ScrollPane(handTable);
        scrollPane.setScrollingDisabled(true, true);
        scrollPane.setFillParent(true);

        stage.addActor(scrollPane);
        createHand();

        TextButton resetButton = new TextButton("End Turn", skin);
        resetButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resetHand();

                enemyTest.SHIELDS = 0;
                enemyTest.enemy_move();

                Verg.ENERGY = Verg.MAX_ENERGY;
                Verg.SHIELDS = 0;

                return true;
            }
        });
        resetButton.setBounds(1060,120,100,100);
        stage.addActor(resetButton);


        Table uiTable = new Table();
        uiTable.top().pad(15);
        uiTable.setFillParent(true);
        stage.addActor(uiTable);

        remainingLabel = new Label("Remaining: " + deckGenerator.getRemainingCards(), skin);
        discardedLabel = new Label("Discarded: " + deckGenerator.getDiscardedCards(), skin);
        hpLabel = new Label("HP: "+Verg.HP+" / "+vergActor.MAX_HP, skin);
        energyLabel = new Label("Energy: "+Verg.ENERGY+" / "+vergActor.MAX_ENERGY,skin);

        uiTable.add(hpLabel).pad(10);
        uiTable.add(energyLabel).pad(10);
        uiTable.add(remainingLabel).pad(10);
        uiTable.add(discardedLabel).pad(10);

        TextButton return_button = new TextButton("ESC",skin);
        uiTable.add(return_button.pad(10)).right();

        return_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("ESC");
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.setDebugAll(true);
    }

    @Override
    public void show() {

    }

    private void createHand() {
        ArrayList<CardClass> hand;
        if (deckGenerator.getRemainingCards() < Verg.Drawable_cards) { // If there are fewer cards remaining than needed to fill hand
            deckGenerator.discardedBackToDeck(deckGenerator.discardPile); // Reshuffle discardPile back into deck
            hand = deckGenerator.drawCards(Verg.Drawable_cards);
        } else {
            hand = deckGenerator.drawCards(Verg.Drawable_cards);
        }
        for (CardClass card : hand) {
            card.setTargetActor(vergActor);
            handTable.add(card);
        }
        updateLabels();
    }

    private void resetHand() {
        ArrayList<CardClass> currentHand = new ArrayList<>();
        for (Actor actor : handTable.getChildren()) {
            if (actor instanceof CardClass) {
                currentHand.add((CardClass) actor);
            }
        }
        deckGenerator.returnCards(currentHand);
        handTable.clear();
        createHand();
    }
    public void addToDiscardPile(CardClass card) {
        ArrayList<CardClass> currentHand = new ArrayList<>();
        currentHand.add(card);
        deckGenerator.returnCards(currentHand);
        updateLabels();
    }

    private void updateLabels() {
        remainingLabel.setText("Remaining: " + deckGenerator.getRemainingCards());
        discardedLabel.setText("Discarded: " + deckGenerator.getDiscardedCards());
        hpLabel.setText("HP: "+vergActor.HP+" / "+vergActor.MAX_HP);
        energyLabel.setText("Energy: "+vergActor.ENERGY+" / "+vergActor.MAX_ENERGY);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.DARK_GRAY);

        if (Verg.HP < 1){
            LeviathanGame.global_deck = new ArrayList<>();
            game.setScreen(new MainMenuScreen(game)); // For restart
        }
        if (enemyActor.dead){
            if (LeviathanGame.boss_battle){
                LeviathanGame.boss_battle = false;
                LeviathanGame.current_level++;
                if (LeviathanGame.current_level == 3){
                    BG_Music.startMusic("01_STS",true);
                    game.setScreen(new MainMenuScreen(game)); // Win, go back to menu
                    LeviathanGame.current_level = 1;
                    LeviathanGame.global_deck.clear();
                    dispose();
                }else {
                    BG_Music.startMusic("02_STS",true);
                    routeMapScreen.dispose();
                    game.setScreen(new RouteMapScreen(game)); // To go to level 2
                    dispose();
                }
                return;
            }
            Verg.SHIELDS = 0;
            routeMapScreen.updatePathProgress(pathIndex, buttonIndex);
            game.setScreen(routeMapScreen); // To return back to RouteMapScreen
            this.dispose();
            return;
        }
        stage.act(delta);
        stage.draw();
        updateLabels();

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
        //stage.dispose();
    }

    @Override
    public void dispose() {
        LeviathanGame.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
    }
}
