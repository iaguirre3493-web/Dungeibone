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
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    private DungeiboneGame game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private Rectangle player;
    private Rectangle key;
    private Rectangle door;

    private float playerSpeed;

    private int vida;
    private int puntos;
    private int nivel;

    private boolean tieneLlave;
    private boolean keyVisible;

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

        vida = 100;
        puntos = 0;
        nivel = 1;
        playerSpeed = 220;

        cargarNivel(nivel);
    }

    private void cargarNivel(int numeroNivel) {
        tieneLlave = false;
        keyVisible = true;

        player = new Rectangle(60, 60, 40, 40);

        if (numeroNivel == 1) {
            key = new Rectangle(360, 300, 25, 25);
            door = new Rectangle(700, 500, 50, 70);
        } else {
            key = new Rectangle(650, 100, 25, 25);
            door = new Rectangle(700, 500, 50, 70);
        }
    }

    @Override
    public void render(float delta) {
        updatePlayer(delta);
        checkCollisions();
        clearScreen();
        drawGame();
        drawHUD();
    }

    private void updatePlayer(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.x -= playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.x += playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.y += playerSpeed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.y -= playerSpeed * delta;
        }

        if (player.x < 0) {
            player.x = 0;
        }

        if (player.y < 0) {
            player.y = 0;
        }

        if (player.x > 800 - player.width) {
            player.x = 800 - player.width;
        }

        if (player.y > 600 - player.height) {
            player.y = 600 - player.height;
        }
    }

    private void checkCollisions() {
        if (keyVisible && player.overlaps(key)) {
            keyVisible = false;
            tieneLlave = true;
            puntos += 100;
        }

        if (player.overlaps(door) && tieneLlave) {
            if (nivel == 1) {
                nivel = 2;
                cargarNivel(nivel);
            } else {
                System.out.println("Victoria");
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawGame() {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(0, 0, 800, 600);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(player.x, player.y, player.width, player.height);

        if (keyVisible) {
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(key.x, key.y, key.width, key.height);
        }

        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(door.x, door.y, door.width, door.height);

        shapeRenderer.end();
    }

    private void drawHUD() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Vida: " + vida, 20, 580);
        font.draw(batch, "Puntos: " + puntos, 20, 550);
        font.draw(batch, "Nivel: " + nivel, 20, 520);

        if (tieneLlave) {
            font.draw(batch, "Llave: SI", 20, 490);
        } else {
            font.draw(batch, "Llave: NO", 20, 490);
        }

        font.getData().setScale(1);
        font.draw(batch, "Objetivo: recoge la llave y llega a la puerta", 250, 580);

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
