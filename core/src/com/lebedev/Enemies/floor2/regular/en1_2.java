package com.lebedev.Enemies.floor2.regular;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

import static com.badlogic.gdx.utils.Align.center;

public class en1_2 extends enemyTest {
    private static final int ENEMY_WIDTH_PIXEL = 17;
    private static final int ENEMY_HEIGHT_PIXEL = 32;
    private static final int ENEMY_WIDTH = ENEMY_WIDTH_PIXEL * 7;
    private static final int ENEMY_HEIGHT = ENEMY_HEIGHT_PIXEL * 7;
    private static final float ENEMY_ANIMATION_SPEED = 0.5f;
    private static final float ROLL_SWITCH_TIME = 0.65f;
    private static float enemy_x  = 830;
    private static float enemy_y = 190;
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
    private Label hpLabel = new Label("11",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));
    /***MOVESET ICONS***/
    private Texture shield = new Texture("assets/Pictures/Sprites/Effects/defence.png");
    private final int icon_width = 52;
    private final int icon_height = 52;
    private Label shieldLabel = new Label("22",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));

    private Texture attack = new Texture("assets/Pictures/Sprites/Effects/attack.png");
    private Label attackLabel = new Label("0",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));

    private Texture buff = new Texture("assets/Pictures/Sprites/Effects/buff.png");
    /***BUFFS & DEBUFFS (effects)***/
    private final int effects_width = 36;
    private final int effects_height = 36;
    private Texture strength_texture = new Texture("assets/Pictures/Sprites/Effects/strength.png");
    private Label effect_Label1 = new Label("1",new Skin(Gdx.files.internal("assets/skin2/uiskin.json")));
    /********STATS*********/
    public static int MAX_HP = 50;
    public static int HP = 50;
    public static int SHIELDS = 10;
    public static int pattern = 1;
    public static int strength = 0;
    public static boolean dead = false;

    public en1_2(){
        TextureRegion[][] rollSpriteSheet = TextureRegion.split(
                new Texture("assets/Pictures/Sprites/enemyDEMO.png"),
                ENEMY_WIDTH_PIXEL, ENEMY_HEIGHT_PIXEL);
        {
            rolls[0] = new Animation(ENEMY_ANIMATION_SPEED, rollSpriteSheet[2]); //  (hit)
            rolls[1] = new Animation(ENEMY_ANIMATION_SPEED, rollSpriteSheet[1]); // (attack)
            rolls[2] = new Animation(ENEMY_ANIMATION_SPEED, rollSpriteSheet[0]);  //(idle)
            rolls[3] = new Animation(ENEMY_ANIMATION_SPEED, rollSpriteSheet[3]); //(defend)
            rolls[4] = new Animation(ENEMY_ANIMATION_SPEED, rollSpriteSheet[4]); // (ego)
        }

        setBounds(enemy_x,enemy_y,ENEMY_WIDTH-10,ENEMY_HEIGHT-30);
        setTouchable(Touchable.enabled);


        shieldLabel.setPosition(enemy_x - 84, enemy_y-30);
        shieldLabel.setAlignment(center);
        hpLabel.setPosition(enemy_x + 36, enemy_y-32);
        hpLabel.setAlignment(center);
        attackLabel.setPosition(enemy_x +32, enemy_y+222);
        attackLabel.setAlignment(center);
        effect_Label1.setPosition(enemy_x  - 42, enemy_y-59);
        effect_Label1.setAlignment(center);
    }
    public static void set_stats(int hp, int maxHP){
        HP = hp;
        MAX_HP = maxHP;
    }

    public void manage_HP(int amount) {
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
        System.out.println("\n-------\nEnemy HP: " + HP+"\nSHIELDS: " + SHIELDS);
        if (SHIELDS<0){
            SHIELDS = 0; //No need to show negative shields
        }
    }
    public static void enemy_move(){
        switch (pattern){
            case 1:
                roll = 1;
                Verg.manage_HP(-6 - strength);
                break;
            case 2:
                HP += 7;
                if (HP > MAX_HP){
                    HP = MAX_HP;
                }
                strength += 2;
                break;
            case 3:
                SHIELDS += 10;
                break;
        }
        if (pattern>=3){
            pattern = 0;
        }
        pattern +=1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        /***PATTERN UPDATE***/
        if (HP >= 1) { // Enemy is alive
            batch.draw(texture_empty, enemy_x - 50, enemy_y - 30, bar_width, bar_height);//static hp bar frame

            float ratio = (float) HP / MAX_HP; // dynamic hp bar
            batch.draw(texture_full, enemy_x - 44, enemy_y - 24, (hp_width * ratio), hp_height);
            hpLabel.draw(batch,parentAlpha);
            hpLabel.setText(HP+"/"+MAX_HP);
            /***************/
            if (SHIELDS >= 1){
                batch.draw(shield,enemy_x - 98, enemy_y-44, icon_width, icon_height);
                shieldLabel.draw(batch,parentAlpha);
                shieldLabel.setText(SHIELDS);
            } else {
                shieldLabel.remove();
            }
            /***EFFECTS***/
            if (strength != 0 ){
                batch.draw(strength_texture,enemy_x - 40, enemy_y-58, effects_width, effects_height);
                effect_Label1.draw(batch,parentAlpha);
                effect_Label1.setText(strength);
            }
            /***NEXT TURN MOVE***/
            switch (pattern){
                case 1:
                    batch.draw(attack,enemy_x + 30, enemy_y+220, icon_width, icon_height);
                    attackLabel.draw(batch,parentAlpha);
                    attackLabel.setText(6+strength);
                    break;
                case 2:
                    batch.draw(buff,enemy_x + 30, enemy_y+220, icon_width, icon_height);
                    break;
                case 3:
                    batch.draw(shield,enemy_x + 30, enemy_y+220, icon_width, icon_height);
                    break;
            }
            /***************/
            stateTime += Gdx.graphics.getDeltaTime();
            if (roll != 2) {
                batch.draw((TextureRegion)
                        rolls[roll].getKeyFrame(stateTime, true), enemy_x, enemy_y, ENEMY_WIDTH, ENEMY_HEIGHT);

                rollTimer += Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_SWITCH_TIME) {
                    rollTimer = 0;
                    roll = 2;
                }
            } else {
                batch.draw((TextureRegion)
                        rolls[roll].getKeyFrame(stateTime, true), enemy_x, enemy_y, ENEMY_WIDTH, ENEMY_HEIGHT);
            }
        } else {
            strength = 0; // For reset
            pattern = 1; // For reset
            dead = true;
            this.remove();// Enemy is dead and his actor removed from stage

        }
    }
}
