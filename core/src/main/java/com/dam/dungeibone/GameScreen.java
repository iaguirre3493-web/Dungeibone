package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private static final int OBJETO_MONEDA = 1;
    private static final int OBJETO_TESORO = 2;

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
    private Texture pauseTexture;

    private Texture floorTexture;
    private TextureRegion[][] floorTiles;
    private TextureRegion floorTile;
    private TextureRegion borderTile;

    private TextureRegion[] coinFrames;
    private TextureRegion[] treasureFrames;
    private TextureRegion[] flagFrames;

    private float objectAnimTime;

    private Player player;
    private Rectangle door;

    private ArrayList<Rectangle> objetos;
    private ArrayList<Boolean> objetosVisibles;
    private ArrayList<Integer> objetosTipos;

    private ArrayList<Enemy> enemies;

    private float patrolBaseSpeed;
    private float chaserBaseSpeed;

    private int vida;
    private int puntos;
    private int nivel;

    private int objetosRecogidos;
    private int objetosNecesarios;

    private float damageCooldown;

    private boolean pausa;
    private int opcionPausa;
    private String[] opcionesPausa;

    private String nombreNivel;

    public GameScreen(DungeiboneGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        font = new BitmapFont(Gdx.files.internal("fonts/dungeibone_clean.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        pickupSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pickup.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));

        coinTexture = new Texture(Gdx.files.internal("sprites/coin.png"));
        treasureTexture = new Texture(Gdx.files.internal("sprites/treasure.png"));
        doorTexture = new Texture(Gdx.files.internal("sprites/door.png"));

        pauseTexture = new Texture(Gdx.files.internal("sprites/pausa.png"));
        pauseTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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

        pausa = false;
        opcionPausa = 0;

        opcionesPausa = new String[]{
            "Continuar partida",
            "Sonido",
            "Volver al menu",
            "Salir del juego"
        };

        cargarNivel(nivel);
    }

    private void cargarNivel(int numeroNivel) {
        damageCooldown = 0;
        pausa = false;
        opcionPausa = 0;

        enemies = new ArrayList<>();
        objetos = new ArrayList<>();
        objetosVisibles = new ArrayList<>();
        objetosTipos = new ArrayList<>();

        objetosRecogidos = 0;

        if (numeroNivel == 1) {
            nombreNivel = "Cripta inicial";

            player = new Player(60, 60, 40, 40, 220);
            door = new Rectangle(690, 490, 45, 55);

            addObjeto(360, 300, 35, 35, OBJETO_MONEDA);

            enemies.add(new StaticEnemy(250, 180, 40, 40));
            enemies.add(new EnemyPatrol(420, 420, 40, 40, patrolBaseSpeed, 100, 650));
            enemies.add(new EnemyChaser(620, 160, 40, 40, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new StaticEnemy(520, 300, 40, 40));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyPatrol(180, 350, 40, 40, patrolBaseSpeed, 100, 600));
            }

        } else if (numeroNivel == 2) {
            nombreNivel = "Camara verde";

            player = new Player(60, 500, 40, 40, 220);
            door = new Rectangle(690, 70, 45, 55);

            addObjeto(150, 150, 35, 35, OBJETO_MONEDA);
            addObjeto(390, 460, 35, 35, OBJETO_MONEDA);
            addObjeto(640, 320, 55, 45, OBJETO_TESORO);

            enemies.add(new StaticEnemy(360, 250, 45, 45));
            enemies.add(new EnemyPatrol(160, 390, 45, 45, patrolBaseSpeed, 100, 680));
            enemies.add(new EnemyChaser(590, 210, 45, 45, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new StaticEnemy(520, 410, 45, 45));
                enemies.add(new EnemyPatrol(480, 120, 45, 45, patrolBaseSpeed, 250, 700));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyChaser(690, 420, 45, 45, chaserBaseSpeed));
            }

        } else if (numeroNivel == 3) {
            nombreNivel = "Mazmorra oscura";

            player = new Player(700, 60, 40, 40, 220);
            door = new Rectangle(55, 490, 45, 55);

            addObjeto(640, 500, 35, 35, OBJETO_MONEDA);
            addObjeto(440, 350, 35, 35, OBJETO_MONEDA);
            addObjeto(210, 180, 35, 35, OBJETO_MONEDA);
            addObjeto(110, 420, 55, 45, OBJETO_TESORO);

            enemies.add(new StaticEnemy(330, 250, 50, 50));
            enemies.add(new EnemyPatrol(110, 120, 45, 45, patrolBaseSpeed, 80, 560));
            enemies.add(new EnemyPatrol(500, 470, 45, 45, patrolBaseSpeed, 260, 720));
            enemies.add(new EnemyChaser(570, 250, 45, 45, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new StaticEnemy(220, 370, 45, 45));
                enemies.add(new EnemyChaser(390, 120, 45, 45, chaserBaseSpeed));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyPatrol(650, 330, 45, 45, patrolBaseSpeed, 420, 730));
            }

        } else {
            nombreNivel = "Sala del tesoro final";

            player = new Player(60, 60, 40, 40, 220);
            door = new Rectangle(690, 500, 45, 55);

            addObjeto(120, 500, 35, 35, OBJETO_MONEDA);
            addObjeto(280, 340, 35, 35, OBJETO_MONEDA);
            addObjeto(470, 470, 35, 35, OBJETO_MONEDA);
            addObjeto(620, 190, 35, 35, OBJETO_MONEDA);
            addObjeto(660, 370, 60, 50, OBJETO_TESORO);

            enemies.add(new StaticEnemy(210, 230, 50, 50));
            enemies.add(new StaticEnemy(510, 300, 50, 50));
            enemies.add(new EnemyPatrol(150, 430, 45, 45, patrolBaseSpeed, 80, 700));
            enemies.add(new EnemyPatrol(430, 140, 45, 45, patrolBaseSpeed, 180, 720));
            enemies.add(new EnemyChaser(610, 260, 45, 45, chaserBaseSpeed));
            enemies.add(new EnemyChaser(350, 500, 45, 45, chaserBaseSpeed));

            if (game.getDificultad().equals("NORMAL") || game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyPatrol(300, 390, 45, 45, patrolBaseSpeed, 160, 620));
            }

            if (game.getDificultad().equals("DIFICIL")) {
                enemies.add(new EnemyChaser(710, 120, 45, 45, chaserBaseSpeed));
                enemies.add(new StaticEnemy(390, 230, 50, 50));
            }
        }

        objetosNecesarios = objetos.size();
    }

    private void addObjeto(float x, float y, float width, float height, int tipo) {
        objetos.add(new Rectangle(x, y, width, height));
        objetosVisibles.add(true);
        objetosTipos.add(tipo);
    }

    @Override
    public void render(float delta) {
        handlePauseInput();

        clearScreen();

        if (!pausa) {
            objectAnimTime += delta;

            player.update(delta);
            updateEnemies(delta);
            checkCollisions(delta);
        }

        drawGame();
        drawHUD();

        if (pausa) {
            drawPauseScreen();
        }
    }

    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pausa = !pausa;
        }

        if (!pausa) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            opcionPausa--;

            if (opcionPausa < 0) {
                opcionPausa = opcionesPausa.length - 1;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            opcionPausa++;

            if (opcionPausa >= opcionesPausa.length) {
                opcionPausa = 0;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            ejecutarOpcionPausa();
        }
    }

    private void ejecutarOpcionPausa() {
        if (opcionPausa == 0) {
            pausa = false;
        }

        if (opcionPausa == 1) {
            game.setSonidoActivado(!game.isSonidoActivado());
        }

        if (opcionPausa == 2) {
            game.setScreen(new FirstScreen(game));
        }

        if (opcionPausa == 3) {
            Gdx.app.exit();
        }
    }

    private void updateEnemies(float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player, nivel);
        }
    }

    private void checkCollisions(float delta) {
        damageCooldown -= delta;

        for (int i = 0; i < objetos.size(); i++) {
            if (objetosVisibles.get(i) && player.getBounds().overlaps(objetos.get(i))) {
                objetosVisibles.set(i, false);
                objetosRecogidos++;

                if (objetosTipos.get(i) == OBJETO_MONEDA) {
                    puntos += 100;
                } else {
                    puntos += 250;
                }

                if (game.isSonidoActivado()) {
                    pickupSound.play();
                }
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
            return;
        }

        if (player.getBounds().overlaps(door) && objetosRecogidos >= objetosNecesarios) {
            if (nivel < 4) {
                nivel++;
                cargarNivel(nivel);
            } else {
                game.setScreen(new VictoryScreen(game, puntos));
            }
        }
    }

    private void clearScreen() {
        if (nivel == 1) {
            Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1);
        } else if (nivel == 2) {
            Gdx.gl.glClearColor(0.03f, 0.09f, 0.05f, 1);
        } else if (nivel == 3) {
            Gdx.gl.glClearColor(0.04f, 0.03f, 0.10f, 1);
        } else {
            Gdx.gl.glClearColor(0.12f, 0.04f, 0.02f, 1);
        }

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

    private void drawLevelDecoration() {
        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (nivel == 1) {
            shapeRenderer.setColor(0.05f, 0.15f, 0.35f, 0.28f);
            shapeRenderer.rect(32, 32, 736, 536);

            shapeRenderer.setColor(0.10f, 0.28f, 0.55f, 0.35f);
            shapeRenderer.rect(80, 260, 640, 80);
            shapeRenderer.rect(350, 90, 90, 410);

        } else if (nivel == 2) {
            shapeRenderer.setColor(0.02f, 0.28f, 0.10f, 0.30f);
            shapeRenderer.rect(32, 32, 736, 536);

            shapeRenderer.setColor(0.10f, 0.45f, 0.15f, 0.38f);
            shapeRenderer.rect(90, 90, 620, 70);
            shapeRenderer.rect(90, 450, 620, 70);
            shapeRenderer.rect(360, 120, 80, 360);

        } else if (nivel == 3) {
            shapeRenderer.setColor(0.12f, 0.06f, 0.30f, 0.34f);
            shapeRenderer.rect(32, 32, 736, 536);

            shapeRenderer.setColor(0.35f, 0.12f, 0.55f, 0.35f);
            shapeRenderer.rect(80, 120, 250, 55);
            shapeRenderer.rect(470, 120, 250, 55);
            shapeRenderer.rect(80, 430, 250, 55);
            shapeRenderer.rect(470, 430, 250, 55);
            shapeRenderer.rect(365, 170, 70, 260);

        } else {
            shapeRenderer.setColor(0.36f, 0.06f, 0.02f, 0.35f);
            shapeRenderer.rect(32, 32, 736, 536);

            shapeRenderer.setColor(0.70f, 0.20f, 0.04f, 0.40f);
            shapeRenderer.rect(80, 120, 640, 45);
            shapeRenderer.rect(80, 270, 640, 45);
            shapeRenderer.rect(80, 420, 640, 45);

            shapeRenderer.setColor(0.85f, 0.55f, 0.08f, 0.35f);
            shapeRenderer.rect(350, 80, 100, 440);
        }

        if (objetosRecogidos < objetosNecesarios) {
            shapeRenderer.setColor(0.55f, 0.02f, 0.02f, 0.35f);
            shapeRenderer.rect(door.x - 6, door.y - 6, door.width + 12, door.height + 12);
        } else {
            shapeRenderer.setColor(0.05f, 0.55f, 0.12f, 0.35f);
            shapeRenderer.rect(door.x - 6, door.y - 6, door.width + 12, door.height + 12);
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawGame() {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawTileBackground();

        batch.end();

        drawLevelDecoration();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 0; i < objetos.size(); i++) {
            if (objetosVisibles.get(i)) {
                Rectangle objeto = objetos.get(i);

                if (objetosTipos.get(i) == OBJETO_MONEDA) {
                    batch.draw(getCoinFrame(), objeto.x, objeto.y, objeto.width, objeto.height);
                } else {
                    batch.draw(getTreasureFrame(), objeto.x, objeto.y, objeto.width, objeto.height);
                }
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

        font.setColor(Color.WHITE);
        font.getData().setScale(0.55f);

        font.draw(batch, "Vida: " + vida, 25, 575);
        font.draw(batch, "Puntos: " + puntos, 25, 545);
        font.draw(batch, "Nivel: " + nivel + " / 4", 25, 515);
        font.draw(batch, "Objetos: " + objetosRecogidos + " / " + objetosNecesarios, 25, 485);

        font.getData().setScale(0.42f);

        if (objetosRecogidos >= objetosNecesarios) {
            font.setColor(Color.GREEN);
            font.draw(batch, "Bandera: ABIERTA", 25, 455);
        } else {
            font.setColor(Color.RED);
            font.draw(batch, "Bandera: BLOQUEADA", 25, 455);
        }

        font.setColor(Color.WHITE);
        font.draw(batch, "P - Pausa", 25, 425);

        font.getData().setScale(0.36f);
        font.draw(batch, nombreNivel, 560, 575);

        batch.end();
    }

    private void drawPauseScreen() {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(pauseTexture, 0, 0, 800, 600);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0, 0, 0.88f);
        shapeRenderer.rect(245, 65, 310, 270);

        shapeRenderer.setColor(0.32f, 0.16f, 0.06f, 1);
        shapeRenderer.rect(258, 78, 284, 244);

        shapeRenderer.setColor(0.70f, 0.42f, 0.12f, 1);
        shapeRenderer.rect(258, 312, 284, 10);
        shapeRenderer.rect(258, 78, 284, 10);
        shapeRenderer.rect(258, 78, 10, 244);
        shapeRenderer.rect(532, 78, 10, 244);

        shapeRenderer.setColor(0.17f, 0.07f, 0.03f, 1);
        shapeRenderer.rect(282, 112, 236, 180);

        for (int i = 0; i < opcionesPausa.length; i++) {
            if (i == opcionPausa) {
                shapeRenderer.setColor(0.75f, 0.45f, 0.10f, 1);
            } else {
                shapeRenderer.setColor(0.25f, 0.11f, 0.04f, 1);
            }

            shapeRenderer.rect(300, 250 - i * 40, 200, 28);
        }

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.setColor(Color.WHITE);
        font.getData().setScale(0.43f);

        for (int i = 0; i < opcionesPausa.length; i++) {
            String texto = "";

            if (i == 0) {
                texto = "Continuar partida";
            }

            if (i == 1) {
                if (game.isSonidoActivado()) {
                    texto = "Sonido: ACTIVADO";
                } else {
                    texto = "Sonido: DESACTIVADO";
                }
            }

            if (i == 2) {
                texto = "Volver al menu";
            }

            if (i == 3) {
                texto = "Salir del juego";
            }

            if (i == opcionPausa) {
                texto = "> " + texto + " <";
            }

            drawCenteredMenuLine(texto, 270 - i * 40);
        }

        font.getData().setScale(0.32f);
        drawCenteredMenuLine("ARRIBA / ABAJO - Mover", 105);
        drawCenteredMenuLine("ENTER - Seleccionar    P - Continuar", 88);

        batch.end();
    }

    private void drawCenteredMenuLine(String text, float y) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);

        float x = 400 - layout.width / 2f;

        font.setColor(Color.BLACK);
        font.draw(batch, text, x + 2, y - 2);

        font.setColor(Color.WHITE);
        font.draw(batch, text, x, y);
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

        coinTexture.dispose();
        treasureTexture.dispose();
        doorTexture.dispose();
        pauseTexture.dispose();
        floorTexture.dispose();

        if (player != null) {
            player.dispose();
        }

        if (enemies != null) {
            for (Enemy enemy : enemies) {
                enemy.dispose();
            }
        }
    }
}
