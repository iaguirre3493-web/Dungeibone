package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {

    private float speed;
    private boolean moving;
    private float animationTime;
    private Texture texture;

    public Player(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, null);
        this.speed = speed;
        this.moving = false;
        this.animationTime = 0;
        this.texture = new Texture(Gdx.files.internal("sprites/player.png"));
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

    private boolean isAnimationFrameActive() {
        return ((int) (animationTime * 8)) % 2 == 0;
    }

    public void drawSprite(SpriteBatch batch) {
        float drawWidth = bounds.width;
        float drawHeight = bounds.height;

        if (moving && !isAnimationFrameActive()) {
            drawWidth = bounds.width - 6;
            drawHeight = bounds.height - 6;
        }

        float offsetX = (bounds.width - drawWidth) / 2;
        float offsetY = (bounds.height - drawHeight) / 2;

        batch.draw(texture, bounds.x + offsetX, bounds.y + offsetY, drawWidth, drawHeight);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Ya no se usa para el jugador, ahora se dibuja con SpriteBatch.
    }

    public void dispose() {
        texture.dispose();
    }
}
