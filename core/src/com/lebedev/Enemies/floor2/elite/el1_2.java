package com.lebedev.Enemies.floor2.elite;

import com.badlogic.gdx.graphics.Texture;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

public class el1_2 extends enemyTest {

    /********STATS*********/
    public static int MAX_HP = 100;
    public static int HP = 90;
    public static int SHIELDS = 0;
    public static int attack_damage = 14;

    public static int moveType = 3; // 1: attack, 2: buff, 3: shield
    public static int pattern = 1;
    public static int patternLimit = 4;


    public static Texture enemySprite = new Texture("assets/Pictures/Sprites/DEMO.png");

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
                enemyTest.SHIELDS += 18;
                moveType = 2;
                break;
            case 2:
                enemyTest.HP += 8;
                if (enemyTest.HP > enemyTest.MAX_HP){
                    enemyTest.HP = enemyTest.MAX_HP;
                }
                enemyTest.strength += 4;
                moveType = 1;
                break;
            case 3:
                enemyTest.roll = 1;
                Verg.manage_HP(-14-enemyTest.strength);
                enemyTest.attack_damage = 3;
                moveType = 1;
                break;
            case 4:
                enemyTest.roll = 1;
                Verg.manage_HP(-3-enemyTest.strength);
                enemyTest.attack_damage = 14;
                moveType = 3;
                break;
        }
        if (pattern >= patternLimit) {
            pattern = 0;
        }
        pattern += 1;
        return moveType;
    }

}