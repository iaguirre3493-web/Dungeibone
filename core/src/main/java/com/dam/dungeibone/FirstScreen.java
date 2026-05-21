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

        font = new BitmapFont(Gdx.files.internal("fonts/dungeibone_clean.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();

        backgroundTexture = new Texture(Gdx.files.internal("sprites/dungeibone.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
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

        // Panel del menu
        shapeRenderer.setColor(0, 0, 0, 0.88f);
        shapeRenderer.rect(215, 190, 370, 170);

        shapeRenderer.setColor(0.10f, 0.10f, 0.18f, 0.96f);
        shapeRenderer.rect(227, 202, 346, 146);

        // Panel inferior
        shapeRenderer.setColor(0, 0, 0, 0.92f);
        shapeRenderer.rect(120, 45, 560, 85);

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

        font.getData().setScale(0.50f);
        drawCenteredCleanLine("ENTER - Iniciar partida", 325);
        drawCenteredCleanLine("I - Instrucciones", 290);
        drawCenteredCleanLine("O - Opciones", 255);
        drawCenteredCleanLine("ESC - Salir", 220);

        font.getData().setScale(0.42f);
        drawCenteredCleanLine("Recoge monedas, consigue el tesoro", 112);
        drawCenteredCleanLine("y alcanza la bandera", 88);

        font.getData().setScale(0.34f);
        drawCenteredCleanLine("Videojuego 2D realizado con Java y libGDX", 65);

        batch.end();
    }

    private void drawCenteredCleanLine(String text, float y) {
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
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();

        backgroundTexture.dispose();
        coinTexture.dispose();
        treasureTexture.dispose();
        playerTexture.dispose();
    }
}
