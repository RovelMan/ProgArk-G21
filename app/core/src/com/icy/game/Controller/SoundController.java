package com.icy.game.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class SoundController {
    private static final SoundController ourInstance = new SoundController();
    private Map<String,Sound> sounds;

    public static SoundController getInstance() {
        return ourInstance;
    }

    private SoundController() {
        sounds = new HashMap<>();
    }

    public void add(String name, String path){
        sounds.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    public void play(String name){
        sounds.get(name).play();
    }

    public void remove(String name){
        sounds.get(name).stop();
        sounds.get(name).dispose();
        sounds.remove(name);
    }
}
