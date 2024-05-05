package com.lebedev;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VergVisuals {
    public static final int VERG_WIDTH_PIXEL = 17;
    public static final int VERG_HEIGHT_PIXEL = 32;
    public static final int VERG_WIDTH = VERG_WIDTH_PIXEL * 7;
    public static final int VERG_HEIGHT = VERG_HEIGHT_PIXEL * 7;
    public static final float VERG_ANIMATION_SPEED = 0.5f;
    public static float verg_x, verg_y;
    public static Animation[] rolls;
    public static float rollTimer;
    public static int roll;

    public Animation[] init() {

        verg_y = 200;
        verg_x = LeviathanGame.WIDTH / 4 - VergVisuals.VERG_WIDTH / 4;
        rollTimer = 0;
        roll = 2;

        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(
                new Texture("assets/Pictures/Sprites/DEMO.png"),
                VERG_WIDTH_PIXEL, VERG_HEIGHT_PIXEL);

        rolls[0] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[2]); // right (hit)
        rolls[1] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[1]); // (attack)
        rolls[2] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[0]); // mid (idle)
        rolls[3] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[3]); //(defend)
        rolls[4] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[4]); // left (ego)

        return rolls;
    }
}
