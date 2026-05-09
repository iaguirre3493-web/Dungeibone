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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;

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

    private Texture floorTexture;
    private TextureRegion[][] floorTiles;
    private TextureRegion floorTile;
    private TextureRegion borderTile;

    private TextureRegion[] coinFrames;
    private TextureRegion[] treasureFrames;
    private TextureRegion[] flagFrames;

    private float objectAnimTime;

    private Player player;
    private Rectangle key;
    private Rectangle door;

    private ArrayList<Enemy> enemies;

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

        floorTexture = new Texture(Gdx.files.internal("sprites/floor_tileset.png"));
        floorTiles = TextureRegion.split(floorTexture, 16, 16);

        floorTile = floorTiles[1][1];
        borderTile = floorTiles[0][0];

        TextureRegion[][] tmpCoin = TextureRegion.split(
            coinTexture,
            coinTexture.getWidth() / 4,
            coinTexture.getHeight()
        );
        coinFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            coinFrames[i] = tmpCoin[0][i];
        }

        TextureRegion[][] tmpFlag = TextureRegion.split(
            doorTexture,
            doorTexture.getWidth() / 4,
            doorTexture.getHeight()
        );
        flagFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            flagFrames[i] = tmpFlag[0][i];
        }

        TextureRegion[][] tmpTreasure = TextureRegion.split(
            treasureTexture,
            treasureTexture.getWidth() / 2,
            treasureTexture.getHeight()
        );
        treasureFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            treasureFrames[i] = tmpTreasure[0][i];
        }

        objectAnimTime = 0;

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
        enemies = new ArrayList<>();

        if (numeroNivel == 1) {
            key = new Rectangle(360, 300, 35, 35);
            door = new Rectangle(690, 490, 45, 55);

            enemies.add(new StaticEnemy(250, 180, 40, 40));
            enemies.add(new EnemyPatrol(420, 420, 40, 40, patrolBaseSpeed, 100, 650));
            enemies.add(new EnemyChaser(620, 160, 40, 40, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new StaticEnemy(520, 300, 40, 40));
                enemies.add(new EnemyPatrol(180, 350, 40, 40, patrolBaseSpeed, 100, 600));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyChaser(350, 120, 40, 40, chaserBaseSpeed));
                enemies.add(new EnemyPatrol(580, 230, 40, 40, patrolBaseSpeed, 300, 720));
            }

        } else {
            key = new Rectangle(660, 100, 55, 45);
            door = new Rectangle(690, 490, 45, 55);

            enemies.add(new StaticEnemy(360, 250, 50, 50));
            enemies.add(new EnemyPatrol(250, 420, 45, 45, patrolBaseSpeed, 100, 650));
            enemies.add(new EnemyChaser(560, 220, 45, 45, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new StaticEnemy(180, 330, 45, 45));
                enemies.add(new EnemyPatrol(480, 150, 45, 45, patrolBaseSpeed, 250, 700));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyChaser(690, 360, 45, 45, chaserBaseSpeed));
                enemies.add(new EnemyPatrol(120, 220, 45, 45, patrolBaseSpeed, 80, 500));
            }
        }
    }

    @Override
    public void render(float delta) {
        objectAnimTime += delta;

        player.update(delta);
        updateEnemies(delta);
        checkCollisions(delta);
        clearScreen();
        drawGame();
        drawHUD();
    }

    private void updateEnemies(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player, nivel);
        }
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

        boolean enemyCollision = false;

        for (Enemy enemy : enemies) {
            if (player.getBounds().overlaps(enemy.getBounds())) {
                enemyCollision = true;
            }
        }

        if (enemyCollision && damageCooldown <= 0) {
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

    private TextureRegion getCoinFrame() {
        int frameIndex = (int) (objectAnimTime * 8) % coinFrames.length;
        return coinFrames[frameIndex];
    }

    private TextureRegion getTreasureFrame() {
        int frameIndex = (int) (objectAnimTime * 4) % treasureFrames.length;
        return treasureFrames[frameIndex];
    }

    private TextureRegion getFlagFrame() {
        int frameIndex = (int) (objectAnimTime * 6) % flagFrames.length;
        return flagFrames[frameIndex];
    }

    private void drawTileBackground() {
        for (int x = 0; x < 800; x += 32) {
            for (int y = 0; y < 600; y += 32) {
                batch.draw(floorTile, x, y, 32, 32);
            }
        }

        for (int x = 0; x < 800; x += 32) {
            batch.draw(borderTile, x, 0, 32, 32);
            batch.draw(borderTile, x, 568, 32, 32);
        }

        for (int y = 0; y < 600; y += 32) {
            batch.draw(borderTile, 0, y, 32, 32);
            batch.draw(borderTile, 768, y, 32, 32);
        }
    }

    private void drawMapBackground() {
        // Fondo base
        shapeRenderer.setColor(0.10f, 0.10f, 0.12f, 1);
        shapeRenderer.rect(0, 0, 800, 600);

        // Suelo tipo baldosas
        for (int x = 0; x < 800; x += 40) {
            for (int y = 0; y < 600; y += 40) {
                if ((x / 40 + y / 40) % 2 == 0) {
                    shapeRenderer.setColor(0.13f, 0.13f, 0.16f, 1);
                } else {
                    shapeRenderer.setColor(0.16f, 0.16f, 0.19f, 1);
                }

                shapeRenderer.rect(x, y, 40, 40);
            }
        }

        // Paredes exteriores
        shapeRenderer.setColor(0.04f, 0.04f, 0.06f, 1);
        shapeRenderer.rect(0, 0, 800, 25);
        shapeRenderer.rect(0, 575, 800, 25);
        shapeRenderer.rect(0, 0, 25, 600);
        shapeRenderer.rect(775, 0, 25, 600);

        // Decoración nivel 1
        if (nivel == 1) {
            shapeRenderer.setColor(0.18f, 0.12f, 0.08f, 1);
            shapeRenderer.rect(180, 120, 120, 30);
            shapeRenderer.rect(500, 330, 150, 30);

            shapeRenderer.setColor(0.08f, 0.16f, 0.10f, 1);
            shapeRenderer.rect(90, 430, 80, 80);
            shapeRenderer.rect(610, 80, 90, 70);
        }

        // Decoración nivel 2
        if (nivel == 2) {
            shapeRenderer.setColor(0.18f, 0.06f, 0.06f, 1);
            shapeRenderer.rect(170, 150, 160, 35);
            shapeRenderer.rect(450, 360, 180, 35);

            shapeRenderer.setColor(0.12f, 0.04f, 0.16f, 1);
            shapeRenderer.rect(90, 400, 100, 90);
            shapeRenderer.rect(560, 90, 120, 90);
        }
    }

    private void drawGame() {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawTileBackground();

        if (keyVisible) {
            if (nivel == 1) {
                batch.draw(getCoinFrame(), key.x, key.y, key.width, key.height);
            } else {
                batch.draw(getTreasureFrame(), key.x, key.y, key.width, key.height);
            }
        }

        batch.draw(getFlagFrame(), door.x, door.y, door.width, door.height);

        player.drawSprite(batch);
        for (Enemy enemy : enemies) {
            enemy.drawSprite(batch);
        }

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

        if (nivel == 1) {
            font.draw(batch, "Objetivo: recoge la moneda y llega a la bandera", 280, 555);
        } else {
            font.draw(batch, "Objetivo: recoge el tesoro y llega a la bandera", 280, 555);
        }
        font.draw(batch, "POO: Player, Enemy, StaticEnemy, EnemyPatrol, EnemyChaser", 230, 530);

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
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
        coinTexture.dispose();
        treasureTexture.dispose();
        floorTexture.dispose();
    }
}
