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

public class InstructionsScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture backgroundTexture;

    public InstructionsScreen(DungeiboneGame game) {
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

        backgroundTexture = new Texture(Gdx.files.internal("sprites/instructions.png"));
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

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new FirstScreen(game));
        }
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

        // Sombra del panel
        shapeRenderer.setColor(0, 0, 0, 0.88f);
        shapeRenderer.rect(170, 70, 460, 295);

        // Panel marrón principal
        shapeRenderer.setColor(0.30f, 0.15f, 0.06f, 1);
        shapeRenderer.rect(185, 85, 430, 265);

        // Borde claro
        shapeRenderer.setColor(0.65f, 0.38f, 0.12f, 1);
        shapeRenderer.rect(185, 340, 430, 10);
        shapeRenderer.rect(185, 85, 430, 10);
        shapeRenderer.rect(185, 85, 10, 265);
        shapeRenderer.rect(605, 85, 10, 265);

        // Caja interior oscura
        shapeRenderer.setColor(0.17f, 0.07f, 0.03f, 1);
        shapeRenderer.rect(215, 120, 370, 195);

        // Líneas separadas para que no parezca un párrafo
        shapeRenderer.setColor(0.26f, 0.10f, 0.03f, 1);
        shapeRenderer.rect(230, 285, 340, 24);
        shapeRenderer.rect(230, 252, 340, 24);
        shapeRenderer.rect(230, 219, 340, 24);
        shapeRenderer.rect(230, 186, 340, 24);
        shapeRenderer.rect(230, 153, 340, 24);
        shapeRenderer.rect(230, 120, 340, 24);

        shapeRenderer.end();
    }

    private void drawTexts() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        font.getData().setScale(0.43f);

        drawLeftLine("- Muevete con WASD o flechas", 245, 303);
        drawLeftLine("- Recoge monedas y consigue el tesoro", 245, 270);
        drawLeftLine("- Llega a la bandera para pasar de nivel", 245, 237);
        drawLeftLine("- Evita los enemigos del mapa", 245, 204);
        drawLeftLine("- Pulsa P durante la partida para pausar", 245, 171);
        drawLeftLine("- Supera todos los niveles para ganar", 245, 138);

        font.getData().setScale(0.40f);
        drawCenteredCleanLine("Pulsa ESC para volver al menu", 103);

        batch.end();
    }

    private void drawLeftLine(String text, float x, float y) {
        font.setColor(Color.BLACK);
        font.draw(batch, text, x + 2, y - 2);

        font.setColor(Color.WHITE);
        font.draw(batch, text, x, y);
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
