package com.lebedev.Enemies.floor1.regular;

import com.badlogic.gdx.graphics.Texture;
import com.lebedev.Verg;
import com.lebedev.enemyTest;

public class en1 extends enemyTest {

    /********STATS*********/
    public static int MAX_HP = 40;
    public static int HP = 40;
    public static int SHIELDS = 0;
    public static int attack_damage = 6;

    public static int moveType = 1; // 1: attack, 2: buff, 3: shield
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
        enemyTest.moveType = 1;
    }

    public static int make_move(){
        // 1: attack, 2: buff, 3: shield
        switch (pattern){
            case 1:
                enemyTest.roll = 1;
                Verg.manage_HP(-attack_damage - enemyTest.strength);
                moveType = 2;
                break;
            case 2:
                enemyTest.HP += 7;
                if (enemyTest.HP > enemyTest.MAX_HP){
                    enemyTest.HP = enemyTest.MAX_HP;
                }
                enemyTest.strength += 2;
                moveType = 3;
                break;
            case 3:
                enemyTest.SHIELDS += 10;
                moveType = 1;
                break;
        }
        if (pattern>=patternLimit){
            pattern = 0;
        }
        pattern +=1;
        return moveType;
    }

}
