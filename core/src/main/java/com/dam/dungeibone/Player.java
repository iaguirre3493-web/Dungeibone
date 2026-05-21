package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player extends Entity {

    private float speed;
    private boolean moving;
    private float animationTime;

    private Texture walkTexture;
    private TextureRegion[][] walkFrames;

    private int currentDirection;

    public Player(float x, float y, float width, float height, float speed) {
        super(x, y, width, height, null);
        this.speed = speed;
        this.moving = false;
        this.animationTime = 0;
        this.currentDirection = 0;

        walkTexture = new Texture(Gdx.files.internal("sprites/player_walk.png"));

        walkFrames = TextureRegion.split(walkTexture, 16, 16);
    }

    public void update(float delta) {
        moving = false;
        animationTime += delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            bounds.x -= speed * delta;
            moving = true;
            currentDirection = 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            bounds.x += speed * delta;
            moving = true;
            currentDirection = 3;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            bounds.y += speed * delta;
            moving = true;
            currentDirection = 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            bounds.y -= speed * delta;
            moving = true;
            currentDirection = 0;
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

    private TextureRegion getCurrentFrame() {
        int totalFrames = walkFrames.length;

        if (!moving) {
            return walkFrames[0][currentDirection];
        }

        int frameIndex = (int) (animationTime * 8) % totalFrames;
        return walkFrames[frameIndex][currentDirection];
    }

    public void drawSprite(SpriteBatch batch) {
        batch.draw(getCurrentFrame(), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // El jugador se dibuja con SpriteBatch usando sprites.
    }

    public void dispose() {
        walkTexture.dispose();
    }
}
