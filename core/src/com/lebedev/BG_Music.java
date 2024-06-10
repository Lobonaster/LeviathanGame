package com.lebedev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BG_Music extends ApplicationAdapter {
    private static Music music;
    public static float volume = 0.3f;
    public static void startMusic(String filename, boolean stop_previous) {
        if (stop_previous) {
            System.out.println("done");
            music.dispose();
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("assets/Audio/"+filename+".mp3"));
        music.setLooping(true);
        music.setVolume(volume);
        music.play();
    }
    public static void volumeChange(float newVolume) {
        volume = newVolume;
        music.setVolume(volume);
    }
}
