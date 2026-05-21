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

public class OptionsScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;

    public OptionsScreen(DungeiboneGame game) {
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

        backgroundTexture = new Texture(Gdx.files.internal("sprites/options.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        drawBackgroundImage();
        drawPanel();
        drawTexts();
        handleInput();
    }

    private void drawBackgroundImage() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0, 800, 600);

        batch.end();
    }

    private void drawPanel() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Sombra del panel, bajada
        shapeRenderer.setColor(0, 0, 0, 0.88f);
        shapeRenderer.rect(170, 115, 460, 250);

        // Panel marrón, bajado
        shapeRenderer.setColor(0.30f, 0.15f, 0.06f, 1);
        shapeRenderer.rect(185, 130, 430, 220);

        // Borde claro
        shapeRenderer.setColor(0.65f, 0.38f, 0.12f, 1);
        shapeRenderer.rect(185, 340, 430, 10);
        shapeRenderer.rect(185, 130, 430, 10);
        shapeRenderer.rect(185, 130, 10, 220);
        shapeRenderer.rect(605, 130, 10, 220);

        // Caja interior oscura
        shapeRenderer.setColor(0.17f, 0.07f, 0.03f, 1);
        shapeRenderer.rect(215, 165, 370, 150);

        // Separadores para que no parezca un párrafo
        shapeRenderer.setColor(0.26f, 0.10f, 0.03f, 1);
        shapeRenderer.rect(235, 280, 330, 28);
        shapeRenderer.rect(235, 210, 330, 28);

        shapeRenderer.end();
    }

    private void drawTexts() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(0.54f);

        drawCenteredCleanLine("- Pulsa M para cambiar el sonido", 302);

        if (game.isSonidoActivado()) {
            drawCenteredCleanLine("Estado: ACTIVADO", 272);
        } else {
            drawCenteredCleanLine("Estado: DESACTIVADO", 272);
        }

        drawCenteredCleanLine("- Pulsa D para cambiar la dificultad", 232);
        drawCenteredCleanLine("Dificultad actual: " + game.getDificultad(), 202);

        font.getData().setScale(0.43f);
        drawCenteredCleanLine("Pulsa ESC para volver al menu", 155);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.cambiarSonido();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            game.cambiarDificultad();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new FirstScreen(game));
        }
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
