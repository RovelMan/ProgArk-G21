package com.icy.game.Controller;

import com.badlogic.gdx.Input;

/**
 * Created by jotde on 19.03.2018.
 */

public class MyTextInputListener implements Input.TextInputListener {

    @Override
    public void input(String text) {
        System.out.println("Input: " + text);
    }

    @Override
    public void canceled() {

    }
}
