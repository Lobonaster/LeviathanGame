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

import java.util.ArrayList;
import java.util.Random;

import static com.lebedev.LeviathanGame.flag;

public class EventScreen implements Screen {

    LeviathanGame game;
    private Skin skin = new Skin(Gdx.files.internal("assets/skin2/uiskin.json"));
    private ExtendViewport extendViewport;
    private Stage stage;
    private RouteMapScreen routeMapScreen;
    private int pathIndex;
    private int buttonIndex;
    private Label hpLabel = new Label("",skin);
    private Label textLabel = new Label("",skin);
    private Label remainingLabel = new Label("",skin);
    private DeckGenerator deckGenerator;
    Verg vergActor = new Verg();
    enemyTest enemyActor = new enemyTest();

    public EventScreen(LeviathanGame game,RouteMapScreen routeMapScreen, int pathIndex, int buttonIndex){
        this.game = game;
        this.routeMapScreen = routeMapScreen;
        this.pathIndex = pathIndex;
        this.buttonIndex = buttonIndex;

        extendViewport = new ExtendViewport(1280, 720+flag);
        stage = new Stage(extendViewport);

        LeviathanGame.inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(LeviathanGame.inputMultiplexer);

        Table root = new Table();
        root.setFillParent(true);

        deckGenerator = new DeckGenerator( this ,vergActor,enemyActor);

        PictureClass bg1 = new PictureClass();
        bg1.get_assets("BGS/stage1.png", 0, 0, 1280,720);
        stage.addActor(bg1);

        PictureClass ui_bar = new PictureClass();
        ui_bar.get_assets("Menus/Ui_Bar.png", 0,
                (int) extendViewport.getMinWorldHeight()-80+LeviathanGame.flag, 1280,80);
        stage.addActor(ui_bar);

        PictureClass bg = new PictureClass();
        bg.get_assets("BGS/event.png", 0, 0, 1280,720);
        stage.addActor(bg);

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
        /**
         * deciding event type based on randomness
         * WHERE:
         * 0 - Plus 8 MAX and HP / Get a strong strike card
         * 1 - Increase DrawCard and decrease 10 HP / Increase MaxEnergy and heavily decrease HP and 18 MaxHp / Skip
         * 2 - Heal 10HP and get defence card/ get 1 strength and 7 DMG/ get 14 MaxHP and curse
         */
        PictureClass pic = new PictureClass();

        final Random buttonRandom = new Random();
        int event_type = buttonRandom.nextInt(3);
        System.out.println(event_type);

        TextButton choice1 = new TextButton("",skin);
        TextButton choice2 = new TextButton("",skin);
        TextButton choice3 = new TextButton("",skin);

        switch(event_type){
            case 0:
                pic.get_assets("Buttons/hmm.png", 55, 70, 460,460);
                textLabel = new Label("Life and death",skin);
                choice1.setText("+8 HP and MaxHP");
                choice1.getLabel().setColor(Color.GREEN);
                choice2.setText("Get a strong strike card");
                choice2.getLabel().setColor(Color.GREEN);
                break;
            case 1:
                pic.get_assets("Buttons/girya.png", 55, 70, 460,460);
                textLabel = new Label("Momentary opportunity",skin);
                choice1.setText("Increase DrawCard and decrease 10 HP");
                choice1.getLabel().setColor(Color.ORANGE);
                choice2.setText("Increase MaxEnergy by 1 and decrease HP and MaxHp by 18");
                choice2.getLabel().setColor(Color.RED);
                choice3.setText("Skip");
                choice3.getLabel().setColor(Color.WHITE);
                break;
            case 2:
                pic.get_assets("Buttons/torture.png", 55, 70, 460,460);
                textLabel = new Label("Slight relief",skin);
                choice1.setText("Heal 10HP and get strong defence card");
                choice1.getLabel().setColor(Color.GREEN);
                choice2.setText("Get 1 strength and 7 DMG");
                choice2.getLabel().setColor(Color.ORANGE);
                choice3.setText("Get 14 MaxHP and curse");
                choice3.getLabel().setColor(Color.DARK_GRAY);
                break;
        }

        stage.addActor(pic);

        hpLabel = new Label("HP: "+Verg.HP+" / "+Verg.MAX_HP, skin);
        hpLabel.setAlignment(Align.center);
        hpLabel.setWrap(false);
        hpLabel.setFontScale(1.5f);
        hpLabel.setBounds(100,650+flag*2,180,60);
        stage.addActor(hpLabel);

        int result = 10 + DeckGenerator.global_deck.size();
        remainingLabel = new Label("Deck: "+result+" Cards", skin);
        remainingLabel.setAlignment(Align.center);
        remainingLabel.setWrap(false);
        remainingLabel.setFontScale(1.5f);
        remainingLabel.setBounds(300,650+flag*2,180,60);
        stage.addActor(remainingLabel);


        textLabel.setAlignment(Align.center);
        textLabel.setColor(Color.BLACK);
        textLabel.setWrap(true);
        textLabel.setFontScale(1.0f);
        textLabel.setBounds(140,565,240,30);
        stage.addActor(textLabel);

        updateLabel();


        Table menuButtons = new Table();

        root.add(menuButtons).expandY().expandX().right().padTop(200);

        menuButtons.defaults().padRight(70).spaceTop(20).width(600).height(80);

        menuButtons.row();


        menuButtons.add(choice1).padTop(50+flag);
        choice1.getLabel().setAlignment(Align.left);
        choice1.getLabelCell().padRight(40);

        menuButtons.row();
;
        menuButtons.add(choice2);
        choice2.getLabel().setAlignment(Align.left);
        choice2.getLabelCell().padRight(40);

        if (event_type == 1 || event_type == 2){

        menuButtons.row();

        menuButtons.add(choice3);
        choice3.getLabel().setAlignment(Align.left);
        choice3.getLabelCell().padRight(40);
        }

        choice1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (event_type){
                    case 0:
                        Verg.MAX_HP+=8;
                        Verg.heal_HP(8);
                        break;
                    case 1:
                        Verg.Drawable_cards++;
                        Verg.manage_HP(-8);
                        break;
                    case 2:
                        Verg.HP += 10;
                        deckGenerator.addCard("defend");
                        break;
                }
                routeMapScreen.updatePathProgress(pathIndex, buttonIndex);
                game.setScreen(routeMapScreen); // To return back to RouteMapScreen
                dispose();
            }
        });

        choice2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                switch (event_type){
                    case 0:
                        deckGenerator.addCard("verticalSlash");
                        break;
                    case 1:
                        Verg.MAX_ENERGY++;
                        Verg.MAX_HP -= 18;
                        Verg.heal_HP(-18);
                        break;
                    case 2:
                        Verg.strength++;
                        Verg.heal_HP(-7);
                        break;
                }

                if (Verg.HP < 1){
                    DeckGenerator.global_deck = new ArrayList<>(); //clear new cards deck
                    game.setScreen(new MainMenuScreen(game)); // For restart
                    return;
                }

                routeMapScreen.updatePathProgress(pathIndex, buttonIndex);
                game.setScreen(routeMapScreen); // To return back to RouteMapScreen
                dispose();
            }
        });

        choice3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                switch (event_type){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Verg.MAX_HP += 14;
                        deckGenerator.addCard("curse");
                        break;
                }

                if (Verg.HP < 1){
                    LeviathanGame.global_deck = new ArrayList<>(); //clear new cards deck
                    game.setScreen(new MainMenuScreen(game)); // For restart
                    return;
                }

                routeMapScreen.updatePathProgress(pathIndex, buttonIndex);
                game.setScreen(routeMapScreen); // To return back to RouteMapScreen
                dispose();
            }
        });


        stage.addActor(root);
        stage.setDebugAll(true);

    }

    private void updateLabel() {
        hpLabel.setText("HP: "+Verg.HP+" / "+Verg.MAX_HP);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SLATE);

        stage.draw();
        stage.act(delta);
        updateLabel();
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
