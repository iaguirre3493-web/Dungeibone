package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {

    private DungeiboneGame game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private float playerX;
    private float playerY;
    private float playerSize;
    private float playerSpeed;

    public GameScreen(DungeiboneGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();

        playerX = 400;
        playerY = 300;
        playerSize = 40;
        playerSpeed = 220;
    }

    @Override
    public void render(float delta) {
        updatePlayer(delta);
        clearScreen();
        drawPlayer();
    }

    private void updatePlayer(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerX -= playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerX += playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerY += playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerY -= playerSpeed * delta;
        }

        if (playerX < 0) {
            playerX = 0;
        }

        if (playerY < 0) {
            playerY = 0;
        }

        if (playerX > 800 - playerSize) {
            playerX = 800 - playerSize;
        }

        if (playerY > 600 - playerSize) {
            playerY = 600 - playerSize;
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawPlayer() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(playerX, playerY, playerSize, playerSize);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
