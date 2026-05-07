package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class VictoryScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private int puntosFinales;

    public VictoryScreen(DungeiboneGame game, int puntosFinales) {
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        font.getData().setScale(3);
        font.draw(batch, "VICTORIA", 300, 410);

        font.getData().setScale(1.8f);
        font.draw(batch, "Has escapado con el tesoro", 210, 340);

        font.getData().setScale(1.5f);
        font.draw(batch, "Puntuacion final: " + puntosFinales, 270, 290);

        font.getData().setScale(1.2f);
        font.draw(batch, "Pulsa ENTER para volver al menu", 250, 230);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new FirstScreen(game));
        }
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
    }
}
