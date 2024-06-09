package com.lebedev.Enemies.floor2.boss;

import com.badlogic.gdx.graphics.Texture;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

public class b1_2 extends enemyTest {

    /********STATS*********/
    public static int MAX_HP = 400;
    public static int HP = 400;
    public static int SHIELDS = 0;
    public static int attack_damage = 10;

    public static int moveType = 3; // 1: attack, 2: buff, 3: shield
    public static int pattern = 1;
    public static int patternLimit = 3;
    public static boolean trigger = true;

    public static Texture enemySprite = new Texture("assets/Pictures/Sprites/enemyDEMO.png");

    public static void init(){
        enemyTest.enemySprite = enemySprite;
        enemyTest.MAX_HP= MAX_HP;
        enemyTest.HP = HP;
        enemyTest.SHIELDS = SHIELDS;
        enemyTest.attack_damage = attack_damage;
        pattern = 1;
        patternLimit = 3;
        enemyTest.moveType = 1;
    }

    public static int make_move() {
        // 1: attack, 2: buff, 3: shield
        if (enemyTest.HP >= 200){
            switch (pattern) {
                case 1:
                    enemyTest.roll = 1;
                    Verg.manage_HP(-10-enemyTest.strength);
                    enemyTest.attack_damage = 16;
                    moveType = 1;
                    break;
                case 2:
                    enemyTest.roll = 1;
                    Verg.manage_HP(-16-enemyTest.strength);
                    enemyTest.attack_damage = 10;
                    moveType = 2;
                    break;
                case 3:
                    enemyTest.HP += 20;
                    if (enemyTest.HP > enemyTest.MAX_HP){
                        enemyTest.HP = enemyTest.MAX_HP;
                    }
                    enemyTest.strength += 2;
                    moveType = 1;
                    break;
            }
        }else{
            if(!trigger){ // one time phase 2 activation
                switch (pattern) {
                    case 1:
                        enemyTest.roll = 1;
                        Verg.manage_HP(-26 - enemyTest.strength);
                        enemyTest.attack_damage = 13;
                        moveType = 1;
                        break;
                    case 2:
                        enemyTest.roll = 1;
                        Verg.manage_HP(-13 - enemyTest.strength);
                        enemyTest.attack_damage = 26;
                        strength += 4;
                        moveType = 1;
                        break;
                }
            }else { // one time phase 2 activation
                pattern = 0;
                patternLimit = 2;
                moveType = 1;
                enemyTest.phaseRoll = 4;
                enemyTest.roll = 4;
                enemyTest.attack_damage = 26;
                trigger = false;
                System.out.println("RAGE!");
            }
        }

        if (pattern >= patternLimit) {
            pattern = 0;
        }
        pattern += 1;
        return moveType;
    }

}