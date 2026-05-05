package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionsScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    public OptionsScreen(DungeiboneGame game) {
        this.game = game;
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
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        font.getData().setScale(2.5f);
        font.draw(batch, "OPCIONES", 305, 440);

        font.getData().setScale(1.4f);

        if (game.isSonidoActivado()) {
            font.draw(batch, "M - Sonido: ACTIVADO", 230, 340);
        } else {
            font.draw(batch, "M - Sonido: DESACTIVADO", 230, 340);
        }

        font.draw(batch, "D - Dificultad: " + game.getDificultad(), 230, 300);

        font.getData().setScale(1.2f);
        font.draw(batch, "Pulsa ESC para volver al menu", 260, 160);

        batch.end();

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
