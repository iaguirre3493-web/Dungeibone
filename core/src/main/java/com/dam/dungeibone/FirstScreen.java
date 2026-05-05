package com.dam.dungeibone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FirstScreen implements Screen {

    private DungeiboneGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    public FirstScreen() {
    }

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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        font.getData().setScale(3);
        font.draw(batch, "DUNGEIBONE", 260, 360);

        font.getData().setScale(1.5f);
        font.draw(batch, "Pulsa ENTER para comenzar", 250, 300);

        font.getData().setScale(1);
        font.draw(batch, "Version minima - libGDX", 310, 250);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("Comenzar partida");
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
