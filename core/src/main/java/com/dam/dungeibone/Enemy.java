package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends Entity {

    protected static final int DOWN = 0;
    protected static final int UP = 1;
    protected static final int LEFT = 2;
    protected static final int RIGHT = 3;

    protected float speed;
    protected float animationTime;
    protected Texture texture;
    protected TextureRegion[][] frames;

    protected boolean moving;
    protected int direction;

    public Enemy(float x, float y, float width, float height, Color color, float speed, String texturePath, int initialDirection) {
        super(x, y, width, height, color);
        this.speed = speed;
        this.animationTime = 0;
        this.texture = new Texture(Gdx.files.internal(texturePath));
        this.frames = TextureRegion.split(texture, 16, 16);
        this.moving = false;
        this.direction = initialDirection;
    }

    public void update(float delta, Player player, int nivel) {
        animationTime += delta;
        moving = false;
    }

    protected TextureRegion getCurrentFrame() {
        int safeDirection = direction;

        if (safeDirection < 0) {
            safeDirection = DOWN;
        }

        if (safeDirection >= frames[0].length) {
            safeDirection = DOWN;
        }

        if (!moving) {
            return frames[0][safeDirection];
        }

        int frameCount = Math.min(4, frames.length);
        int frameIndex = (int) (animationTime * 6) % frameCount;

        return frames[frameIndex][safeDirection];
    }

    public void drawSprite(SpriteBatch batch) {
        batch.draw(getCurrentFrame(), bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Los enemigos se dibujan con SpriteBatch.
    }

    public void dispose() {
        texture.dispose();
    }
}
