package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {

    private DungeiboneGame game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private Sound pickupSound;
    private Sound hitSound;

    private Texture coinTexture;
    private Texture treasureTexture;
    private Texture doorTexture;

    private Player player;
    private Rectangle key;
    private Rectangle door;

    private StaticEnemy staticEnemy;
    private EnemyPatrol patrolEnemy;
    private EnemyChaser chaserEnemy;

    private float patrolBaseSpeed;
    private float chaserBaseSpeed;

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

        coinTexture = new Texture(Gdx.files.internal("sprites/coin.png"));
        treasureTexture = new Texture(Gdx.files.internal("sprites/treasure.png"));
        doorTexture = new Texture(Gdx.files.internal("sprites/door.png"));

        vida = 100;
        puntos = 0;
        nivel = 1;

        if (game.getDificultad().equals("FACIL")) {
            patrolBaseSpeed = 100;
            chaserBaseSpeed = 60;
        } else if (game.getDificultad().equals("NORMAL")) {
            patrolBaseSpeed = 140;
            chaserBaseSpeed = 90;
        } else {
            patrolBaseSpeed = 180;
            chaserBaseSpeed = 130;
        }

        damageCooldown = 0;

        cargarNivel(nivel);
    }

    private void cargarNivel(int numeroNivel) {
        tieneLlave = false;
        keyVisible = true;
        damageCooldown = 0;

        player = new Player(60, 60, 40, 40, 220);

        if (numeroNivel == 1) {
            key = new Rectangle(360, 300, 25, 25);
            door = new Rectangle(700, 500, 50, 70);

            staticEnemy = new StaticEnemy(250, 180, 40, 40);
            patrolEnemy = new EnemyPatrol(420, 420, 40, 40, patrolBaseSpeed, 100, 650);
            chaserEnemy = new EnemyChaser(620, 160, 40, 40, chaserBaseSpeed);
        } else {
            key = new Rectangle(670, 100, 50, 40);
            door = new Rectangle(700, 500, 50, 70);

            staticEnemy = new StaticEnemy(360, 250, 50, 50);
            patrolEnemy = new EnemyPatrol(250, 420, 45, 45, patrolBaseSpeed, 100, 650);
            chaserEnemy = new EnemyChaser(560, 220, 45, 45, chaserBaseSpeed);
        }
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        updateEnemies(delta);
        checkCollisions(delta);
        clearScreen();
        drawGame();
        drawHUD();
    }

    private void updateEnemies(float delta) {
        staticEnemy.update(delta, player, nivel);
        patrolEnemy.update(delta, player, nivel);
        chaserEnemy.update(delta, player, nivel);
    }

    private void checkCollisions(float delta) {
        damageCooldown -= delta;

        if (keyVisible && player.getBounds().overlaps(key)) {
            keyVisible = false;
            tieneLlave = true;
            puntos += 100;

            if (game.isSonidoActivado()) {
                pickupSound.play();
            }
        }

        if (player.getBounds().overlaps(staticEnemy.getBounds())
            || player.getBounds().overlaps(patrolEnemy.getBounds())
            || player.getBounds().overlaps(chaserEnemy.getBounds())) {

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

        if (player.getBounds().overlaps(door) && tieneLlave) {
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

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (keyVisible) {
            if (nivel == 1) {
                batch.draw(coinTexture, key.x, key.y, key.width, key.height);
            } else {
                batch.draw(treasureTexture, key.x, key.y, key.width, key.height);
            }
        }

        batch.draw(doorTexture, door.x, door.y, door.width, door.height);

        player.drawSprite(batch);
        staticEnemy.drawSprite(batch);
        patrolEnemy.drawSprite(batch);
        chaserEnemy.drawSprite(batch);

        batch.end();
    }

    private void drawHUD() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        font.getData().setScale(1.5f);
        font.draw(batch, "Vida: " + vida, 20, 580);
        font.draw(batch, "Puntos: " + puntos, 20, 550);
        font.draw(batch, "Nivel: " + nivel, 20, 520);

        if (tieneLlave) {
            font.draw(batch, "Objeto: SI", 20, 490);
        } else {
            font.draw(batch, "Objeto: NO", 20, 490);
        }

        font.getData().setScale(1);
        font.draw(batch, "Rojo: estatico | Naranja: patrulla | Morado: perseguidor", 230, 580);
        font.draw(batch, "Objetivo: recoge el tesoro y llega al barco", 280, 555);
        font.draw(batch, "POO: Player, Enemy, StaticEnemy, PatrolEnemy, ChaserEnemy", 230, 530);

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
        pickupSound.dispose();
        hitSound.dispose();
        doorTexture.dispose();
        player.dispose();
        staticEnemy.dispose();
        patrolEnemy.dispose();
        chaserEnemy.dispose();
        coinTexture.dispose();
        treasureTexture.dispose();
    }
}
