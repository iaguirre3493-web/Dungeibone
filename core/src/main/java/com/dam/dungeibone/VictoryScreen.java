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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class VictoryScreen implements Screen {

    private DungeiboneGame game;
    private int puntosFinales;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;

    public VictoryScreen(DungeiboneGame game, int puntosFinales) {
        this.game = game;
        this.puntosFinales = puntosFinales;
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

        backgroundTexture = new Texture(Gdx.files.internal("sprites/victory.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        drawBackground();
        drawPanels();
        drawTexts();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new FirstScreen(game));
        }
    }

    private void drawBackground() {
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 800, 600);
        batch.end();
    }

    private void drawPanels() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Sombra
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(145, 190, 510, 190);

        // Recuadro marrón
        shapeRenderer.setColor(0.32f, 0.16f, 0.06f, 1);
        shapeRenderer.rect(160, 205, 480, 160);

        // Borde claro
        shapeRenderer.setColor(0.65f, 0.38f, 0.12f, 1);
        shapeRenderer.rect(160, 355, 480, 10);
        shapeRenderer.rect(160, 205, 480, 10);
        shapeRenderer.rect(160, 205, 10, 160);
        shapeRenderer.rect(630, 205, 10, 160);

        // Interior oscuro para que el texto se lea mejor
        shapeRenderer.setColor(0.22f, 0.10f, 0.04f, 1);
        shapeRenderer.rect(180, 225, 440, 115);

        shapeRenderer.end();
    }

    private void drawTexts() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.setColor(Color.WHITE);

        font.getData().setScale(0.65f);
        drawCenteredCleanLine("Has escapado con el tesoro", 320);

        font.getData().setScale(0.55f);
        drawCenteredCleanLine("Puntuacion final: " + puntosFinales, 285);

        font.getData().setScale(0.45f);
        drawCenteredCleanLine("Pulsa ENTER para volver al menu", 250);

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
    }
}
