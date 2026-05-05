package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {

    private DungeiboneGame game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private float playerX;
    private float playerY;
    private float playerSize;
    private float playerSpeed;

    private int vida;
    private int puntos;
    private int nivel;

    public GameScreen(DungeiboneGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        playerX = 400;
        playerY = 300;
        playerSize = 40;
        playerSpeed = 220;

        vida = 100;
        puntos = 0;
        nivel = 1;
    }

    @Override
    public void render(float delta) {
        updatePlayer(delta);
        clearScreen();
        drawPlayer();
        drawHUD();
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

    private void drawHUD() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Vida: " + vida, 20, 580);
        font.draw(batch, "Puntos: " + puntos, 20, 550);
        font.draw(batch, "Nivel: " + nivel, 20, 520);
        batch.end();
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
        batch.dispose();
        font.dispose();
    }
}
