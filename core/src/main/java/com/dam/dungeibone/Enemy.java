package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends Entity {

    protected float speed;
    protected float animationTime;

    public Enemy(float x, float y, float width, float height, Color color, float speed) {
        super(x, y, width, height, color);
        this.speed = speed;
        this.animationTime = 0;
    }

    public void update(float delta, Player player, int nivel) {
        animationTime += delta;
    }

    protected boolean isAnimationFrameActive() {
        return ((int) (animationTime * 6)) % 2 == 0;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        float drawWidth = bounds.width;
        float drawHeight = bounds.height;

        if (!isAnimationFrameActive()) {
            drawWidth = bounds.width - 5;
            drawHeight = bounds.height - 5;
        }

        float offsetX = (bounds.width - drawWidth) / 2;
        float offsetY = (bounds.height - drawHeight) / 2;

        shapeRenderer.setColor(color);
        shapeRenderer.rect(bounds.x + offsetX, bounds.y + offsetY, drawWidth, drawHeight);
    }
}
