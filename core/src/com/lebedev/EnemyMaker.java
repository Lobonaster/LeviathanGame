package com.lebedev;

import com.lebedev.Enemies.floor1.boss.b1;
import com.lebedev.Enemies.floor1.elite.el1;
import com.lebedev.Enemies.floor1.elite.el2;
import com.lebedev.Enemies.floor1.regular.en1;
import com.lebedev.Enemies.floor1.regular.en2;
import com.lebedev.Enemies.floor1.regular.en3;
import com.lebedev.Enemies.floor2.regular.en1_2;


import java.util.Random;

public class EnemyMaker {

    private static String current_enemy = "";

    public static void enemyMaker(String enemy_type) {

        final Random enemyRandom = new Random();

        switch (enemy_type){
            case "regular":
                int random_enemy_id = enemyRandom.nextInt(3);
                switch (random_enemy_id){
                    case (0):
                        current_enemy = "en1";
                        en1.init();
                        enemyTest.dead = false;
                        break;
                    case (1):
                        current_enemy = "en2";
                        en2.init();
                        enemyTest.dead = false;
                        break;
                    case (2):
                        current_enemy = "en3";
                        en3.init();
                        enemyTest.dead = false;
                        break;
                }
            break;
            case "elite":
                random_enemy_id = enemyRandom.nextInt(2);
                switch (random_enemy_id) {
                    case (0):
                        current_enemy = "el1";
                        el1.init();
                        enemyTest.dead = false;
                        break;
                    case (1):
                        current_enemy = "el2";
                        el2.init();
                        enemyTest.dead = false;
                        break;
                }
            break;
            case "boss":
                switch (LeviathanGame.current_level){
                    case (1):
                        current_enemy = "b1";
                        b1.init();
                        b1.dead = false;
                        break;
                }
            break;
        }
    }

    public static int get_move(){
        switch (current_enemy){
            case "en1":
                return en1.make_move();
            case "en2":
                return en2.make_move();
            case "en3":
                return en3.make_move();
            case "el1":
                return el1.make_move();
            case "el2":
                return el2.make_move();
            case  "b1":
                return b1.make_move();
        }
        return 0;
    };


}
