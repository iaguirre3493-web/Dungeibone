package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class EnemyChaser extends Enemy {

    public EnemyChaser(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, Color.PURPLE, speed, "sprites/enemy_chaser.png", DOWN);
    }

    @Override
    public void update(float delta, Player player, int nivel) {
        super.update(delta, player, nivel);

        float currentSpeed = speed;

        if (nivel == 2) {
            currentSpeed += 40;
        }

        float distanceX = player.getX() - bounds.x;
        float distanceY = player.getY() - bounds.y;

        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX > 0) {
                bounds.x += currentSpeed * delta;
                direction = RIGHT;
            } else {
                bounds.x -= currentSpeed * delta;
                direction = LEFT;
            }
        } else {
            if (distanceY > 0) {
                bounds.y += currentSpeed * delta;
                direction = UP;
            } else {
                bounds.y -= currentSpeed * delta;
                direction = DOWN;
            }
        }

        moving = true;
    }
}
