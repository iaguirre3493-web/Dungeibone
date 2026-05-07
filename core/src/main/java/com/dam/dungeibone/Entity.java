package com.dam.dungeibone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Entity {

    protected Rectangle bounds;
    protected Color color;

    public Entity(float x, float y, float width, float height, Color color) {
        this.bounds = new Rectangle(x, y, width, height);
        this.color = color;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return bounds.x;
    }

    public float getY() {
        return bounds.y;
    }

    public float getWidth() {
        return bounds.width;
    }

    public float getHeight() {
        return bounds.height;
    }

    public void setPosition(float x, float y) {
        bounds.x = x;
        bounds.y = y;
    }
}
