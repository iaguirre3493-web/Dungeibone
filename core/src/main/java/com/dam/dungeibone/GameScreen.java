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
import com.badlogic.gdx.audio.Sound;

public class GameScreen implements Screen {

    private DungeiboneGame game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Sound pickupSound;
    private Sound hitSound;

    private Rectangle player;
    private Rectangle key;
    private Rectangle door;

    private Rectangle staticEnemy;
    private Rectangle patrolEnemy;
    private Rectangle chaserEnemy;

    private float playerSpeed;
    private float patrolSpeed;
    private float chaserSpeed;
    private int patrolDirection;

    private int vida;
    private int puntos;
    private int nivel;

    private boolean tieneLlave;
    private boolean keyVisible;
    private float damageCooldown;

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
        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));

        pickupSound.play(1.0f);
        System.out.println("Prueba de sonido ejecutada");

        vida = 100;
        puntos = 0;
        nivel = 1;

        playerSpeed = 220;

        if (game.getDificultad().equals("FACIL")) {
            patrolSpeed = 100;
            chaserSpeed = 60;
        } else if (game.getDificultad().equals("NORMAL")) {
            patrolSpeed = 140;
            chaserSpeed = 90;
        } else {
            patrolSpeed = 180;
            chaserSpeed = 130;
        }

        patrolDirection = 1;
        damageCooldown = 0;

        cargarNivel(nivel);
    }

    private void cargarNivel(int numeroNivel) {
        tieneLlave = false;
        keyVisible = true;
        damageCooldown = 0;

        player = new Rectangle(60, 60, 40, 40);

        if (numeroNivel == 1) {
            key = new Rectangle(360, 300, 25, 25);
            door = new Rectangle(700, 500, 50, 70);

            staticEnemy = new Rectangle(250, 180, 40, 40);
            patrolEnemy = new Rectangle(420, 420, 40, 40);
            chaserEnemy = new Rectangle(620, 160, 40, 40);
        } else {
            key = new Rectangle(690, 100, 25, 25);
            door = new Rectangle(700, 500, 50, 70);

            staticEnemy = new Rectangle(360, 250, 50, 50);
            patrolEnemy = new Rectangle(250, 420, 45, 45);
            chaserEnemy = new Rectangle(560, 220, 45, 45);
        }
    }


    @Override
    public void render(float delta) {
        updatePlayer(delta);
        updateEnemies(delta);
        checkCollisions(delta);
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

    private void updateEnemies(float delta) {
        float patrolSpeedActual = patrolSpeed;
        float chaserSpeedActual = chaserSpeed;

        if (nivel == 2) {
            patrolSpeedActual += 50;
            chaserSpeedActual += 40;
        }

        patrolEnemy.x += patrolSpeedActual * patrolDirection * delta;

        if (patrolEnemy.x < 100) {
            patrolDirection = 1;
        }

        if (patrolEnemy.x > 650) {
            patrolDirection = -1;
        }

        if (chaserEnemy.x < player.x) {
            chaserEnemy.x += chaserSpeedActual * delta;
        }

        if (chaserEnemy.x > player.x) {
            chaserEnemy.x -= chaserSpeedActual * delta;
        }

        if (chaserEnemy.y < player.y) {
            chaserEnemy.y += chaserSpeedActual * delta;
        }

        if (chaserEnemy.y > player.y) {
            chaserEnemy.y -= chaserSpeedActual * delta;
        }
    }

    private void checkCollisions(float delta) {
        damageCooldown -= delta;

        if (keyVisible && player.overlaps(key)) {
            keyVisible = false;
            tieneLlave = true;
            puntos += 100;

            if (game.isSonidoActivado()) {
                pickupSound.play();
            }
        }

        if (player.overlaps(staticEnemy) || player.overlaps(patrolEnemy) || player.overlaps(chaserEnemy)) {
            if (damageCooldown <= 0) {
                if (game.getDificultad().equals("FACIL")) {
                    vida -= 5;
                } else if (game.getDificultad().equals("NORMAL")) {
                    vida -= 10;
                } else {
                    vida -= 15;
                }

                if (game.isSonidoActivado()) {
                    hitSound.play();
                }

                damageCooldown = 1;
            }
        }

        if (vida <= 0) {
            game.setScreen(new GameOverScreen(game, puntos));
        }

        if (player.overlaps(door) && tieneLlave) {
            if (nivel == 1) {
                nivel = 2;
                cargarNivel(nivel);
            } else {
                game.setScreen(new VictoryScreen(game, puntos));
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

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(staticEnemy.x, staticEnemy.y, staticEnemy.width, staticEnemy.height);

        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(patrolEnemy.x, patrolEnemy.y, patrolEnemy.width, patrolEnemy.height);

        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(chaserEnemy.x, chaserEnemy.y, chaserEnemy.width, chaserEnemy.height);

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
        font.draw(batch, "Rojo: estatico | Naranja: patrulla | Morado: perseguidor", 230, 580);
        font.draw(batch, "Objetivo: recoge la llave y llega a la puerta", 280, 555);

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
