package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    private float speed;
    private boolean moving;

    public Player(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, Color.GREEN);
        this.speed = speed;
        this.moving = false;
    }

    public void update(float delta) {
        moving = false;

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

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(bounds.x + 9, bounds.y + 26, 6, 6);
        shapeRenderer.rect(bounds.x + 25, bounds.y + 26, 6, 6);
    }
}
