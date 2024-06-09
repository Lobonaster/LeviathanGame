package com.lebedev;

import com.lebedev.Enemies.floor1.boss.b1;
import com.lebedev.Enemies.floor1.elite.el1;
import com.lebedev.Enemies.floor1.elite.el2;
import com.lebedev.Enemies.floor1.regular.en1;
import com.lebedev.Enemies.floor1.regular.en2;
import com.lebedev.Enemies.floor1.regular.en3;

import com.lebedev.Enemies.floor2.boss.b1_2;
import com.lebedev.Enemies.floor2.elite.el1_2;
import com.lebedev.Enemies.floor2.elite.el2_2;
import com.lebedev.Enemies.floor2.regular.en1_2;
import com.lebedev.Enemies.floor2.regular.en2_2;
import com.lebedev.Enemies.floor2.regular.en3_2;


import java.util.Random;

public class EnemyMaker {

    private static String current_enemy = "";

    public static void enemyMaker(String enemy_type) {

        final Random enemyRandom = new Random();

        if (LeviathanGame.current_level == 1) {
            switch (enemy_type) {
                case "regular":
                    int random_enemy_id = enemyRandom.nextInt(3);
                    switch (random_enemy_id) {
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
                    current_enemy = "b1";
                    b1.init();
                    b1.dead = false;
                    break;

            }

        } else if (LeviathanGame.current_level == 2){
            switch (enemy_type) {
                case "regular":
                    int random_enemy_id = enemyRandom.nextInt(3);
                    switch (random_enemy_id) {
                        case (0):
                            current_enemy = "en1_2";
                            en1_2.init();
                            enemyTest.dead = false;
                            break;
                        case (1):
                            current_enemy = "en2_2";
                            en2_2.init();
                            enemyTest.dead = false;
                            break;
                        case (2):
                            current_enemy = "en3_2";
                            en3_2.init();
                            enemyTest.dead = false;
                            break;
                        case (3):
                            current_enemy = "en4_2";
                            en3.init();
                            enemyTest.dead = false;
                            break;
                    }
                    break;
                case "elite":
                    random_enemy_id = enemyRandom.nextInt(2);
                    switch (random_enemy_id) {
                        case (0):
                            current_enemy = "el1_2";
                            el1_2.init();
                            enemyTest.dead = false;
                            break;
                        case (1):
                            current_enemy = "el2_2";
                            el2_2.init();
                            enemyTest.dead = false;
                            break;
                        case (2):
                            current_enemy = "el3_2";
                            en3.init();
                            enemyTest.dead = false;
                            break;
                    }
                    break;
                case "boss":
                    current_enemy = "b1_2";
                    b1_2.init();
                    b1.dead = false;
                    break;

            }
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

            case "en1_2":
                return en1_2.make_move();
            case "en2_2":
                return en2_2.make_move();
            case "en3_2":
                return en3_2.make_move();
            case "el1_2":
                return el1_2.make_move();
            case "el2_2":
                return el2_2.make_move();
            case  "b1_2":
                return b1_2.make_move();
        }
        return 0;
    };


}
