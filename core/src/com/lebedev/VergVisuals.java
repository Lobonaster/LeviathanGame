package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;

public class VergVisuals extends Actor {
    // TODO: 14.05.2024 still needs polishing
    public static final int VERG_WIDTH_PIXEL = 17;
    public static final int VERG_HEIGHT_PIXEL = 32;
    public static final int VERG_WIDTH = VERG_WIDTH_PIXEL * 7;
    public static final int VERG_HEIGHT = VERG_HEIGHT_PIXEL * 7;
    public static final float VERG_ANIMATION_SPEED = 0.5f;
    public static final float ROLL_SWITCH_TIME = 0.65f;
    public static int HP = 10;
    public static int MAX_HP = 40;
    public static float verg_x  = 230;
    private static float verg_y = 170;
    public static float rollTimer = 0;
    public static int roll = 2;
    private float stateTime = 0;
    public static final Animation[] rolls = new Animation[5];

    /******HP BARS*********/
    private final int bar_width = 192;
    private final int bar_height = 18;
    private int hp_width =180;
    private final int hp_height = 9;
    private Texture texture_empty = new Texture("assets/Pictures/Sprites/HP_EMPTY.png");
    private Texture texture_full = new Texture("assets/Pictures/Sprites/HP_FULL.png");
    /**********************/
    boolean hit_receive = false;


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

        setBounds(verg_x,verg_y,VERG_WIDTH,VERG_HEIGHT);
        setTouchable(Touchable.enabled);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //TODO: IT`S A PLACEHOLDER
                //if u click on verg actor box, it will receive some damage
                HP -= 9;
                roll = 2;
                System.out.println("OUCH!");
                hit_receive = true;
                return true;
            }
        });
    }
    public void rollSwitch(int plus_minus){ //prevents roll from going out of bounds, UNUSED for now
        roll += plus_minus;
        if (roll < 0 ){
            roll = 0;
        } else if (roll > 4) {
            roll = 4;
        }
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        /***HP UPDATE***/
        if (HP >= 1) { // Verg is alive
            batch.draw(texture_empty, verg_x - 50, verg_y - 50, bar_width, bar_height);//static hp bar frame
            float ratio = (float) HP / MAX_HP; // dynamic hp bar}

            if (hit_receive) {
                System.out.println(ratio);
                batch.draw(texture_full, verg_x - 50, verg_y - 50, (hp_width * ratio), hp_height);
                hit_receive = false;
            } else {
                batch.draw(texture_full, verg_x - 44, verg_y - 44, (hp_width * ratio), hp_height);
            }

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


