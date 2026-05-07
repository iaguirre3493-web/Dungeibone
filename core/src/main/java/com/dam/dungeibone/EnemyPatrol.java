package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class EnemyPatrol extends Enemy {

    private int direction;
    private float minX;
    private float maxX;

    public EnemyPatrol(float x, float y, float width, float height, float speed, float minX, float maxX) {
        super(x, y, width, height, Color.ORANGE, speed, "sprites/enemy_patrol.png");
        this.direction = 1;
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void update(float delta, Player player, int nivel) {
        super.update(delta, player, nivel);

        float currentSpeed = speed;

        if (nivel == 2) {
            currentSpeed += 50;
        }

        bounds.x += currentSpeed * direction * delta;

        if (bounds.x < minX) {
            direction = 1;
        }

        if (bounds.x > maxX) {
            direction = -1;
        }
    }
}
