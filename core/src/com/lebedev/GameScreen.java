package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
    public static final float SPEED = 440;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 7;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 7;
    public static final float ROLL_SWITCH_TIME = 0.15f;

    Animation[] rolls;
    Texture BG;
    int roll;
    float x, y;
    float rollTimer;
    float stateTime;
    LeviathanGame game;
    public GameScreen(LeviathanGame game){
        BG = new Texture("assets/Pictures/BGS/stage1.png");
        this.game = game;
        y = 200;
        x = LeviathanGame.WIDTH / 4 - SHIP_WIDTH / 4;
        roll = 2;
        rollTimer = 0;
        /**
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(
                        new Texture("assets/Pictures/Sprites/DEMO.png"),
                        SHIP_WIDTH_PIXEL,SHIP_HEIGHT_PIXEL);

        rolls[0] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[2]); //right
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[1]);
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]); // mid
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[3]);
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED, rollSpriteSheet[4]); //left
         */
        VergVisuals verg = new VergVisuals();
        rolls = verg.init(rolls,SHIP_WIDTH_PIXEL,SHIP_HEIGHT_PIXEL);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();

            if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - SHIP_WIDTH;
            }
            //update roll if button just clicked
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll < 4)
            {
                rollTimer = 0;
                roll++;
            }
            //update roll
            rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_SWITCH_TIME){
                rollTimer = 0;
                roll ++;
                if (roll > 4 ){
                    roll = 4;
                }
            }
        } else {
            if (roll > 2 ){
                //update roll to go back into mid pos
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_SWITCH_TIME){
                    rollTimer = 0;
                    roll --;
                    if (roll < 0 ){
                        roll = 0;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();

            if (x < 0 ){
                x = 0;
            }
            //update roll if button just clicked
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll <= 0)
            {
                rollTimer = 0;
                roll--;
            }
            //update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_SWITCH_TIME){
                rollTimer = 0;
                roll --;
                if (roll < 0 ){
                    roll = 0;
                }
            }
        } else {
            if (roll < 2 ){
                //update roll to go back into mid pos
                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_SWITCH_TIME){
                    rollTimer = 0;
                    roll ++;
                    if (roll > 4 ){
                        roll = 4;
                    }
                }
            }
        }

        stateTime += delta;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(BG,0,0, LeviathanGame.WIDTH, LeviathanGame.HEIGHT);

        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true),x,y, SHIP_WIDTH, SHIP_HEIGHT);

        game.batch.end();
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
