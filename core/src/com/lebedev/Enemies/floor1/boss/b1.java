package com.lebedev.Enemies.floor1.boss;

import com.badlogic.gdx.graphics.Texture;
import com.lebedev.SoundMaker;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

public class b1 extends enemyTest {

    /********STATS*********/
    public static int MAX_HP = 220;
    public static int HP = 120;
    public static int SHIELDS = 20;
    public static int attack_damage = 10;

    public static int moveType = 3; // 1: attack, 2: buff, 3: shield
    public static int pattern = 1;
    public static int patternLimit = 3;


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
                enemyTest.HP += 100;
                if (enemyTest.HP > enemyTest.MAX_HP){
                    enemyTest.HP = enemyTest.MAX_HP;
                }
                moveType = 2;
                break;
            case 2:
                enemyTest.HP -= 80;
                if (enemyTest.HP > enemyTest.MAX_HP){
                    enemyTest.HP = enemyTest.MAX_HP;
                }
                enemyTest.strength += 4;
                moveType = 1;
                break;
            case 3:
                SoundMaker.makeSound("swordSnd");
                enemyTest.roll = 1;
                Verg.manage_HP(-10-enemyTest.strength);
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