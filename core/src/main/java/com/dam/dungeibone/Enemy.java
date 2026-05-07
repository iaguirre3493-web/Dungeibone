package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends Entity {

    protected float speed;

    public Enemy(float x, float y, float width, float height, Color color, float speed) {
        super(x, y, width, height, color);
        this.speed = speed;
    }

    public void update(float delta, Player player, int nivel) {
        // Método vacío. Cada enemigo tendrá su propio comportamiento.
    }
}
