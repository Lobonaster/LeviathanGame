package com.lebedev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;

public class SoundMaker extends ApplicationAdapter {
    static Sound sound;
    static float soundLen;
    static long id;
    public static void makeSound(String fileName){

        sound = Gdx.audio.newSound(Gdx.files.internal("assets/Audio/" + fileName +".mp3"));
        switch (fileName) {
            case "swordSnd":
                id = sound.play(0.4f, 1.3f, 0f);
                soundLen = 1.36f;
                break;
            case "click":
                id = sound.play(1f, 1f, 0f);
                soundLen = 0.21f;
                break;
        }
        Timer.schedule(new Timer.Task() {
           @Override
           public void run() {
               sound.dispose();
           }
        },soundLen);
    }

}
