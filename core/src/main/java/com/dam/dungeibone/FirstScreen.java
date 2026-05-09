package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FirstScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private Texture backgroundTexture;
    private Texture coinTexture;
    private Texture treasureTexture;
    private Texture playerTexture;

    private TextureRegion[] coinFrames;
    private TextureRegion[] treasureFrames;
    private TextureRegion[] playerFrames;

    private float animationTime;

    public FirstScreen(DungeiboneGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("sprites/menu_background.jpeg"));
        coinTexture = new Texture(Gdx.files.internal("sprites/coin.png"));
        treasureTexture = new Texture(Gdx.files.internal("sprites/treasure.png"));
        playerTexture = new Texture(Gdx.files.internal("sprites/player_walk.png"));

        TextureRegion[][] tmpCoin = TextureRegion.split(
            coinTexture,
            coinTexture.getWidth() / 4,
            coinTexture.getHeight()
        );

        coinFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            coinFrames[i] = tmpCoin[0][i];
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

        TextureRegion[][] tmpPlayer = TextureRegion.split(playerTexture, 16, 16);

        playerFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            playerFrames[i] = tmpPlayer[0][i];
        }

        animationTime = 0;
    }

    @Override
    public void render(float delta) {
        animationTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        drawBackgroundImage();
        drawPanels();
        drawAnimatedObjects();
        drawTexts();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.setScreen(new InstructionsScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            game.setScreen(new OptionsScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void drawBackgroundImage() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 800, 600);
        batch.end();
    }

    private void drawPanels() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Panel del título
        shapeRenderer.setColor(0, 0, 0, 0.82f);
        shapeRenderer.rect(170, 385, 460, 90);

        shapeRenderer.setColor(0.45f, 0.27f, 0.05f, 1);
        shapeRenderer.rect(185, 400, 430, 60);

        shapeRenderer.setColor(0.85f, 0.55f, 0.10f, 1);
        shapeRenderer.rect(195, 410, 410, 40);

        // Panel del menú MÁS GRANDE
        shapeRenderer.setColor(0, 0, 0, 0.88f);
        shapeRenderer.rect(220, 190, 360, 170);

        shapeRenderer.setColor(0.10f, 0.10f, 0.18f, 0.96f);
        shapeRenderer.rect(232, 202, 336, 146);

        // Panel inferior MÁS ALTO y MÁS ARRIBA
        shapeRenderer.setColor(0, 0, 0, 0.90f);
        shapeRenderer.rect(120, 45, 560, 75);

        shapeRenderer.end();
    }

    private TextureRegion getCoinFrame() {
        int frameIndex = (int) (animationTime * 8) % coinFrames.length;
        return coinFrames[frameIndex];
    }

    private TextureRegion getTreasureFrame() {
        int frameIndex = (int) (animationTime * 3) % treasureFrames.length;
        return treasureFrames[frameIndex];
    }

    private TextureRegion getPlayerFrame() {
        int frameIndex = (int) (animationTime * 6) % playerFrames.length;
        return playerFrames[frameIndex];
    }

    private void drawAnimatedObjects() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float bounce = (float) Math.sin(animationTime * 4) * 4;

        // Jugador abajo izquierda
        batch.draw(getPlayerFrame(), 110, 95 + bounce, 90, 90);

        // Cofre abajo derecha
        batch.draw(getTreasureFrame(), 575, 95 + bounce, 90, 70);

        // Montoncito de monedas junto al cofre
        batch.draw(getCoinFrame(), 530, 88, 24, 24);
        batch.draw(getCoinFrame(), 550, 82, 24, 24);
        batch.draw(getCoinFrame(), 570, 88, 24, 24);
        batch.draw(getCoinFrame(), 545, 102, 24, 24);
        batch.draw(getCoinFrame(), 565, 105, 24, 24);
        batch.draw(getCoinFrame(), 590, 86, 24, 24);
        batch.draw(getCoinFrame(), 610, 82, 24, 24);
        batch.draw(getCoinFrame(), 625, 92, 24, 24);

        batch.end();
    }

    private void drawTexts() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        GlyphLayout layout = new GlyphLayout();

        // ===== TÍTULO =====
        font.getData().setScale(3.4f);
        layout.setText(font, "DUNGEIBONE");
        float titleX = (800 - layout.width) / 2f;
        float titleY = 445;

        font.setColor(Color.BLACK);
        font.draw(batch, "DUNGEIBONE", titleX + 3, titleY - 3);

        font.setColor(Color.WHITE);
        font.draw(batch, "DUNGEIBONE", titleX, titleY);

        // ===== MENÚ =====
        font.getData().setScale(1.25f);

        drawCenteredLine("ENTER - Iniciar partida", 325);
        drawCenteredLine("I - Instrucciones", 290);
        drawCenteredLine("O - Opciones", 255);
        drawCenteredLine("ESC - Salir", 220);

        // ===== TEXTO DE ABAJO MÁS ARRIBA =====
        font.getData().setScale(1.05f);
        drawCenteredStrongLine("Recoge monedas, consigue el tesoro", 118);
        drawCenteredStrongLine("y alcanza la bandera", 94);

        font.getData().setScale(0.88f);
        drawCenteredStrongLine("Videojuego 2D realizado con Java y libGDX", 68);

        font.setColor(Color.WHITE);

        batch.end();
    }

    private void drawCenteredLine(String text, float y) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float x = (800 - layout.width) / 2f;

        font.setColor(Color.BLACK);
        font.draw(batch, text, x + 2, y - 2);

        font.setColor(Color.WHITE);
        font.draw(batch, text, x, y);
    }

    private void drawCenteredStrongLine(String text, float y) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        float x = (800 - layout.width) / 2f;

        font.setColor(Color.BLACK);
        font.draw(batch, text, x - 1, y);
        font.draw(batch, text, x + 1, y);
        font.draw(batch, text, x, y - 1);
        font.draw(batch, text, x, y + 1);
        font.draw(batch, text, x - 1, y - 1);
        font.draw(batch, text, x + 1, y - 1);
        font.draw(batch, text, x - 1, y + 1);
        font.draw(batch, text, x + 1, y + 1);

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
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();

        backgroundTexture.dispose();
        coinTexture.dispose();
        treasureTexture.dispose();
        playerTexture.dispose();
    }
}
