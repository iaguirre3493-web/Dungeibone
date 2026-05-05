package com.dam.dungeibone;

import com.badlogic.gdx.Game;

public class DungeiboneGame extends Game {

    @Override
    public void create() {
        setScreen(new FirstScreen(this));
    }
}
