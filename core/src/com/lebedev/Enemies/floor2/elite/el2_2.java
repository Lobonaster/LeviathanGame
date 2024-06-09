package com.lebedev.Enemies.floor2.elite;

import com.badlogic.gdx.graphics.Texture;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

public class el2_2 extends enemyTest {

    /********STATS*********/
    public static int MAX_HP = 44;
    public static int HP = 44;
    public static int SHIELDS = 45;
    public static int attack_damage = 6;

    public static int moveType = 2; // 1: attack, 2: buff, 3: shield
    public static int pattern = 1;
    public static int patternLimit = 2;


    public static Texture enemySprite = new Texture("assets/Pictures/Sprites/enemyDEMO.png");

    public static void init(){
        enemyTest.enemySprite = enemySprite;
        enemyTest.MAX_HP= MAX_HP;
        enemyTest.HP = HP;
        enemyTest.SHIELDS = SHIELDS;
        enemyTest.attack_damage = attack_damage;
        pattern = 1;
        enemyTest.moveType = 3;
    }

    public static int make_move() {
        // 1: attack, 2: buff, 3: shield
        switch (pattern) {
            case 1:
                enemyTest.MAX_HP += 7;
                enemyTest.HP += 7 + enemyTest.strength;
                if (enemyTest.HP > enemyTest.MAX_HP){
                    enemyTest.HP = enemyTest.MAX_HP;
                }
                enemyTest.strength += 4;
                enemyTest.SHIELDS +=10;
                moveType = 1;
                break;
            case 2:
                enemyTest.roll = 1;
                Verg.manage_HP(-attack_damage - enemyTest.strength);
                enemyTest.SHIELDS +=5;
                enemyTest.SHIELDS += enemyTest.strength;
                moveType = 2;
                break;
        }
        if (pattern >= patternLimit) {
            pattern = 0;
        }
        pattern += 1;
        return moveType;
    }

}