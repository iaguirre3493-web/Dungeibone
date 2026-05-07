package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class StaticEnemy extends Enemy {

    public StaticEnemy(float x, float y, float width, float height) {
        super(x, y, width, height, Color.RED, 0, "sprites/enemy_static.png", DOWN);
    }

    @Override
    public void update(float delta, Player player, int nivel) {
        super.update(delta, player, nivel);
        moving = false;
    }
}
