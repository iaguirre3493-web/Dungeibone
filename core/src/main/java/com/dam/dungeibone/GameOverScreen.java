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

public class GameOverScreen implements Screen {

    private DungeiboneGame game;
    private int puntosFinales;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;

    public GameOverScreen(DungeiboneGame game, int puntosFinales) {
        this.game = game;
        this.puntosFinales = puntosFinales;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        shapeRenderer = new ShapeRenderer();
        backgroundTexture = new Texture(Gdx.files.internal("sprites/game_over.png"));
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

        // Sombra del recuadro
        shapeRenderer.setColor(0, 0, 0, 0.85f);
        shapeRenderer.rect(145, 205, 510, 185);

        // Recuadro marrón estilo tierra
        shapeRenderer.setColor(0.30f, 0.15f, 0.06f, 1);
        shapeRenderer.rect(160, 220, 480, 155);

        // Borde marrón claro
        shapeRenderer.setColor(0.55f, 0.32f, 0.12f, 1);
        shapeRenderer.rect(160, 365, 480, 8);
        shapeRenderer.rect(160, 220, 480, 8);
        shapeRenderer.rect(160, 220, 8, 155);
        shapeRenderer.rect(632, 220, 8, 155);

        shapeRenderer.end();
    }

    private void drawTexts() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // NO ponemos aquí "GAME OVER" porque ya está en la imagen

        font.getData().setScale(1.55f);
        drawCenteredStrongLine("Has sido derrotado", 345);

        font.getData().setScale(1.25f);
        drawCenteredStrongLine("Puntuacion final: " + puntosFinales, 305);

        font.getData().setScale(1.05f);
        drawCenteredStrongLine("Pulsa ENTER para volver al menu", 265);

        font.setColor(Color.WHITE);
        batch.end();
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
