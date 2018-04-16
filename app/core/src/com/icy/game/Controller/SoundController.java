package com.icy.game.Controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.icy.game.IcyGame;

import java.util.HashMap;
import java.util.Map;

public class SoundController {
    private static final SoundController ourInstance = new SoundController();
    private Map<String,Sound> sounds;
    private Map<String,Music> music;

    public static SoundController getInstance() {
        return ourInstance;
    }

    private SoundController() {
        sounds = new HashMap<>();
        music = new HashMap<>();
    }

    public void addEffect(String name, String path){
        sounds.put(name, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    public void addMusic(String name, String path){
        music.put(name,Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    public void playEffect(String name){
        music.get(name).setVolume(IcyGame.VOLUME);
        sounds.get(name).play();
    }

    public void playMusic(String name){
        music.get(name).setVolume(IcyGame.VOLUME);
        music.get(name).play();
    }

    public void removeEffect(String name){
        sounds.get(name).stop();
        sounds.get(name).dispose();
        sounds.remove(name);
    }

    public void removeMusic(String name){
        music.get(name).stop();
        music.get(name).dispose();
        music.remove(name);
    }
}
