package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    private float speed;
    private boolean moving;
    private float animationTime;

    public Player(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, Color.GREEN);
        this.speed = speed;
        this.moving = false;
        this.animationTime = 0;
    }

    public void update(float delta) {
        moving = false;
        animationTime += delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            bounds.x -= speed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            bounds.x += speed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            bounds.y += speed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            bounds.y -= speed * delta;
            moving = true;
        }

        keepInsideScreen();
    }

    private void keepInsideScreen() {
        if (bounds.x < 0) {
            bounds.x = 0;
        }

        if (bounds.y < 0) {
            bounds.y = 0;
        }

        if (bounds.x > 800 - bounds.width) {
            bounds.x = 800 - bounds.width;
        }

        if (bounds.y > 600 - bounds.height) {
            bounds.y = 600 - bounds.height;
        }
    }

    public boolean isMoving() {
        return moving;
    }

    private boolean isAnimationFrameActive() {
        return ((int) (animationTime * 8)) % 2 == 0;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        float drawWidth = bounds.width;
        float drawHeight = bounds.height;

        if (moving && !isAnimationFrameActive()) {
            drawWidth = bounds.width - 6;
            drawHeight = bounds.height - 6;
        }

        float offsetX = (bounds.width - drawWidth) / 2;
        float offsetY = (bounds.height - drawHeight) / 2;

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(bounds.x + offsetX, bounds.y + offsetY, drawWidth, drawHeight);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(bounds.x + 9, bounds.y + 26, 6, 6);
        shapeRenderer.rect(bounds.x + 25, bounds.y + 26, 6, 6);
    }
}
