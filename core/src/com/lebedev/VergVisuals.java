package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class VergVisuals extends Actor {
    // TODO: 14.05.2024 still needs polishing
    public static final int VERG_WIDTH_PIXEL = 17;
    public static final int VERG_HEIGHT_PIXEL = 32;
    public static final int VERG_WIDTH = VERG_WIDTH_PIXEL * 7;
    public static final int VERG_HEIGHT = VERG_HEIGHT_PIXEL * 7;
    public static final float VERG_ANIMATION_SPEED = 0.5f;
    public static float verg_x  = 200;
    private static float verg_y = 450;
    public static float rollTimer = 0 ;
    public static int roll = 2;
    private float stateTime = 0;
    static TextureRegion[][] rollSpriteSheet = TextureRegion.split(
            new Texture("assets/Pictures/Sprites/DEMO.png"),
            VERG_WIDTH_PIXEL, VERG_HEIGHT_PIXEL);
    public static Animation[] rolls = new Animation[5];

    static {
        rolls[0] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[2]); // right (hit)
        rolls[1] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[1]); // (attack)
        rolls[2] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[0]);  // mid (idle)
        rolls[3] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[3]); //(defend)
        rolls[4] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[4]); // left (ego)
    }
    public void setPos(int x, int y){
        verg_x = x;
        verg_y = y;
    }
    public void getDelta(float delta){
        stateTime += delta;
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), verg_x, verg_y, VERG_WIDTH, VERG_HEIGHT);
    }

}
