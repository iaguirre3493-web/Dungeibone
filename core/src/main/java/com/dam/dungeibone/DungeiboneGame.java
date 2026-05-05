package com.dam.dungeibone;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class DungeiboneGame extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}