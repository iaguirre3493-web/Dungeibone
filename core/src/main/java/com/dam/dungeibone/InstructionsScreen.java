package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InstructionsScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    public InstructionsScreen(DungeiboneGame game) {
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
        Gdx.gl.glClearColor(0.03f, 0.03f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        font.getData().setScale(2.5f);
        font.draw(batch, "INSTRUCCIONES", 250, 450);

        font.getData().setScale(1.4f);
        font.draw(batch, "Muevete con WASD o flechas.", 180, 360);
        font.draw(batch, "Encuentra y recoge el tesoro.", 180, 320);
        font.draw(batch, "Llega al barco para pasar de nivel.", 180, 280);
        font.draw(batch, "Evita los enemigos: rojo, naranja y morado.", 180, 240);
        font.draw(batch, "Supera el nivel 2 para ganar.", 180, 200);

        font.getData().setScale(1.2f);
        font.draw(batch, "Pulsa ESC para volver al menu", 260, 120);

        batch.end();

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
