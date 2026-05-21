package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class EnemyPatrol extends Enemy {

    private int movementDirection;
    private float minX;
    private float maxX;

    public EnemyPatrol(float x, float y, float width, float height, float speed, float minX, float maxX) {
        super(x, y, width, height, Color.ORANGE, speed, "sprites/enemy_patrol.png", RIGHT);
        this.movementDirection = 1;
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

        bounds.x += currentSpeed * movementDirection * delta;
        moving = true;

        if (movementDirection == 1) {
            direction = RIGHT;
        } else {
            direction = LEFT;
        }

        if (bounds.x < minX) {
            movementDirection = 1;
            direction = RIGHT;
        }

        if (bounds.x > maxX) {
            movementDirection = -1;
            direction = LEFT;
        }
    }
}
