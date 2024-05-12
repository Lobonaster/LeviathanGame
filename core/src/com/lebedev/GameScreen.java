package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.lebedev.VergVisuals.*;

public class GameScreen implements Screen {
    // TODO: 12.05.2024 needs a full remake
    public static final float SPEED = 440;
    public static final float ROLL_SWITCH_TIME = 0.15f;

    Texture BG;
    float stateTime;
    LeviathanGame game;
    public GameScreen(LeviathanGame game){
        BG = new Texture("assets/Pictures/BGS/stage1.png");
        this.game = game;

        VergVisuals verg = new VergVisuals();
        verg.init();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            verg_x += SPEED * Gdx.graphics.getDeltaTime();

            if (verg_x + VERG_WIDTH > Gdx.graphics.getWidth()) {
                verg_x = Gdx.graphics.getWidth() - VERG_WIDTH;
            }
            //update roll if button just clicked
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && VergVisuals.roll < 4)
            {
                VergVisuals.rollTimer = 0;
                VergVisuals.roll++;
            }
            //update roll
            VergVisuals.rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(VergVisuals.rollTimer) > ROLL_SWITCH_TIME){
                VergVisuals.rollTimer = 0;
                VergVisuals.roll ++;
                if (VergVisuals.roll > 4 ){
                    VergVisuals.roll = 4;
                }
            }
        } else {
            if (VergVisuals.roll > 2 ){
                //update roll to go back into mid pos
                VergVisuals.rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(VergVisuals.rollTimer) > ROLL_SWITCH_TIME){
                    VergVisuals.rollTimer = 0;
                    VergVisuals.roll --;
                    if (VergVisuals.roll < 0 ){
                        VergVisuals.roll = 0;
                    }
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            verg_x -= SPEED * Gdx.graphics.getDeltaTime();

            if (verg_x < 0 ){
                verg_x = 0;
            }
            //update roll if button just clicked
            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && VergVisuals.roll <= 0)
            {
                VergVisuals.rollTimer = 0;
                VergVisuals.roll--;
            }
            //update roll
            VergVisuals.rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(VergVisuals.rollTimer) > ROLL_SWITCH_TIME){
                VergVisuals.rollTimer = 0;
                VergVisuals.roll --;
                if (VergVisuals.roll < 0 ){
                    VergVisuals.roll = 0;
                }
            }
        } else {
            if (VergVisuals.roll < 2 ){
                //update roll to go back into mid pos
                VergVisuals.rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(VergVisuals.rollTimer) > ROLL_SWITCH_TIME){
                    VergVisuals.rollTimer = 0;
                    VergVisuals.roll ++;
                    if (VergVisuals.roll > 4 ){
                        VergVisuals.roll = 4;
                    }
                }
            }
        }

        stateTime += delta;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(BG,0,0, LeviathanGame.WIDTH, LeviathanGame.HEIGHT);

        game.batch.draw((TextureRegion) rolls[VergVisuals.roll].getKeyFrame(stateTime, true), verg_x, verg_y, VergVisuals.VERG_WIDTH, VERG_HEIGHT);

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
