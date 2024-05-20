package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;

public class VergVisuals extends Actor {
    // TODO: 14.05.2024 still needs polishing
    /********BASIC*********/
    private static final int VERG_WIDTH_PIXEL = 17;
    private static final int VERG_HEIGHT_PIXEL = 32;
    private static final int VERG_WIDTH = VERG_WIDTH_PIXEL * 7;
    private static final int VERG_HEIGHT = VERG_HEIGHT_PIXEL * 7;
    private static final float VERG_ANIMATION_SPEED = 0.5f;
    private static final float ROLL_SWITCH_TIME = 0.65f;
    private static float verg_x  = 230;
    private static float verg_y = 190;
    private static float rollTimer = 0;
    private static int roll = 2;
    private float stateTime = 0;
    private static final Animation[] rolls = new Animation[5];

    /******HP BARS*********/
    private final int bar_width = 192;
    private final int bar_height = 18;
    private int hp_width =180;
    private final int hp_height = 9;
    private Texture texture_empty = new Texture("assets/Pictures/Sprites/HP_EMPTY.png");
    private Texture texture_full = new Texture("assets/Pictures/Sprites/HP_FULL.png");
    /********STATS*********/
    public static int HP = 40;
    public static int MAX_HP = 40;
    public static int ENERGY = 3;
    public static int MAX_ENERGY = 3;
    public static int SHIELDS = 0; //WIP


    public VergVisuals(){
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(
                new Texture("assets/Pictures/Sprites/DEMO.png"),
                VERG_WIDTH_PIXEL, VERG_HEIGHT_PIXEL);
        {
            rolls[0] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[2]); //  (hit)
            rolls[1] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[1]); // (attack)
            rolls[2] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[0]);  //(idle)
            rolls[3] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[3]); //(defend)
            rolls[4] = new Animation(VERG_ANIMATION_SPEED, rollSpriteSheet[4]); // (ego)
        }

        setBounds(verg_x,verg_y,VERG_WIDTH-10,VERG_HEIGHT-30);
        setTouchable(Touchable.enabled);
    }
    public void rollSwitch(int plus_minus){ //prevents roll from going out of bounds, UNUSED for now
        roll += plus_minus;
        if (roll < 0 ){
            roll = 0;
        } else if (roll > 4) {
            roll = 4;
        }
    }

    public void manage_HP(int amount) {
        if (amount<0){
            roll = 0;
        }
        HP += amount;
        if (HP > MAX_HP){
            HP = MAX_HP; //NO OVERHEALING!
        }
        System.out.println("Target HP: " + HP);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        /***HP UPDATE***/
        if (HP >= 1) { // Verg is alive
            batch.draw(texture_empty, verg_x - 50, verg_y - 30, bar_width, bar_height);//static hp bar frame

            float ratio = (float) HP / MAX_HP; // dynamic hp bar
            batch.draw(texture_full, verg_x - 44, verg_y - 24, (hp_width * ratio), hp_height);
            /***************/

            stateTime += Gdx.graphics.getDeltaTime();
            if (roll != 2) {
                batch.draw((TextureRegion)
                        rolls[roll].getKeyFrame(stateTime, true), verg_x, verg_y, VERG_WIDTH, VERG_HEIGHT);

                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_SWITCH_TIME) {
                    rollTimer = 0;
                    roll = 2;
                }
            } else {
                batch.draw((TextureRegion)
                        rolls[roll].getKeyFrame(stateTime, true), verg_x, verg_y, VERG_WIDTH, VERG_HEIGHT);
            }
        } else {
            this.remove(); // Verg is dead and his actor removed from stage
        }
    }

}


