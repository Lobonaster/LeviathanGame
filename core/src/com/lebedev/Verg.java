package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static com.badlogic.gdx.utils.Align.center;

public class Verg extends Actor {
    /********BASIC*********/
    private static final int VERG_WIDTH_PIXEL = 17;
    private static final int VERG_HEIGHT_PIXEL = 32;
    private static final int VERG_WIDTH = VERG_WIDTH_PIXEL * 7;
    private static final int VERG_HEIGHT = VERG_HEIGHT_PIXEL * 7;
    private static final float VERG_ANIMATION_SPEED = 0.5f;
    private static final float ROLL_SWITCH_TIME = 0.65f;
    private static float verg_x  = 300;
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
    //Shields
    private Texture shield = new Texture("assets/Pictures/Sprites/Effects/defence.png");
    private final int shield_width = 52;
    private final int shield_height = 52;
    private Label shieldLabel = new Label("22",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));
    /***BUFFS & DEBUFFS (effects)***/
    private final int effects_width = 36;
    private final int effects_height = 36;
    private Texture strength_texture = new Texture("assets/Pictures/Sprites/Effects/strength.png");
    private Label effect_Label1 = new Label("1",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));
    /********STATS*********/
    public static int HP = 60;
    public static int MAX_HP = 60;
    public static int ENERGY = 3;
    public static int MAX_ENERGY = 3;
    public static int SHIELDS = 0;
    public static int Drawable_cards = 5;
    public static int strength = 2;


    public Verg(){
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


        shieldLabel.setPosition(verg_x - 84, verg_y-30);
        shieldLabel.setAlignment(center);
        effect_Label1.setPosition(verg_x  - 42, verg_y-59);
        effect_Label1.setAlignment(center);
    }

    public void perform_attack(){
        roll = 1 ;
    }

    public static void manage_HP(int amount) {
        SHIELDS += amount; // Shield take damage first
        if (SHIELDS>=0){ // If Shield stand still, then defence is successful
            roll = 3; // Defence animation
        } else {
            roll = 0; // Hit animation
            HP += SHIELDS; // If taken damage exceeds shields then damage goes to hp
        }
        if (HP > MAX_HP){
            HP = MAX_HP; //NO OVERHEALING!
        }
        System.out.println("\n-------\nVerg HP: " + HP+"\nSHIELDS: " + SHIELDS);
        if (SHIELDS<0){
            SHIELDS = 0; //No need to show negative shields
        }
    }
    public static void heal_HP(int amount) { // When you need to heal trough shields

        System.out.println("before: "+ HP+" / "+MAX_HP);
        HP += amount;

        if (HP > MAX_HP){
            HP = MAX_HP; //NO OVERHEALING!
        }
        System.out.println("after: "+ HP+" / "+MAX_HP);
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
            if (SHIELDS >= 1){
                batch.draw(shield,verg_x - 98, verg_y-44,shield_width, shield_height);
                shieldLabel.draw(batch,parentAlpha);
                shieldLabel.setText(SHIELDS);
            } else {
                shieldLabel.remove();
            }
            /***EFFECTS***/
            if (strength != 0 ){
                batch.draw(strength_texture,verg_x - 40, verg_y-58, effects_width, effects_height);
                effect_Label1.draw(batch,parentAlpha);
                effect_Label1.setText(strength);
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


