package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;

public class EnemyChaser extends Enemy {

    public EnemyChaser(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, Color.PURPLE, speed, "sprites/enemy_chaser.png");
    }

    @Override
    public void update(float delta, Player player, int nivel) {
        super.update(delta, player, nivel);

        float currentSpeed = speed;

        if (nivel == 2) {
            currentSpeed += 40;
        }

        if (bounds.x < player.getX()) {
            bounds.x += currentSpeed * delta;
        }

        if (bounds.x > player.getX()) {
            bounds.x -= currentSpeed * delta;
        }

        if (bounds.y < player.getY()) {
            bounds.y += currentSpeed * delta;
        }

        if (bounds.y > player.getY()) {
            bounds.y -= currentSpeed * delta;
        }
    }
}
