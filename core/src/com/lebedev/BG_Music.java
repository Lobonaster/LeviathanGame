package com.lebedev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BG_Music extends ApplicationAdapter {
    static Music music;
    public static void startMusic(String filename, boolean stop_previous) {
        if (stop_previous) {
            System.out.println("done");
            music.dispose();
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/Audio/"+filename+".mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }
    /**
    @Override
    public void create () {
        System.out.println("!!!");
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/Audio/final.mp3"));
        music.play();

        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                music.play();
            }
        });
    }
         */

}
