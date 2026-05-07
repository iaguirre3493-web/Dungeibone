package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends Entity {

    protected float speed;
    protected float animationTime;
    protected Texture texture;

    public Enemy(float x, float y, float width, float height, Color color, float speed, String texturePath) {
        super(x, y, width, height, color);
        this.speed = speed;
        this.animationTime = 0;
        this.texture = new Texture(Gdx.files.internal(texturePath));
    }

    public void update(float delta, Player player, int nivel) {
        animationTime += delta;
    }

    protected boolean isAnimationFrameActive() {
        return ((int) (animationTime * 6)) % 2 == 0;
    }

    public void drawSprite(SpriteBatch batch) {
        float drawWidth = bounds.width;
        float drawHeight = bounds.height;

        if (!isAnimationFrameActive()) {
            drawWidth = bounds.width - 5;
            drawHeight = bounds.height - 5;
        }

        float offsetX = (bounds.width - drawWidth) / 2;
        float offsetY = (bounds.height - drawHeight) / 2;

        batch.draw(texture, bounds.x + offsetX, bounds.y + offsetY, drawWidth, drawHeight);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        // Los enemigos ahora se dibujan con SpriteBatch usando sprites.
    }

    public void dispose() {
        texture.dispose();
    }
}
