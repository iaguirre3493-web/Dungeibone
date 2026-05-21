package com.dam.dungeibone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class DungeiboneGame extends Game {

    private boolean sonidoActivado;
    private String dificultad;
    private Music backgroundMusic;

    @Override
    public void create() {
        sonidoActivado = true;
        dificultad = "NORMAL";

        cargarMusica();

        setScreen(new FirstScreen(this));
    }

    private void cargarMusica() {
        FileHandle musicFile = Gdx.files.internal("sounds/background.ogg");

        if (musicFile.exists()) {
            backgroundMusic = Gdx.audio.newMusic(musicFile);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.25f);

            if (sonidoActivado) {
                backgroundMusic.play();
            }
        } else {
            backgroundMusic = null;
            System.out.println("No se ha encontrado la musica: assets/sounds/background.ogg");
        }
    }

    public boolean isSonidoActivado() {
        return sonidoActivado;
    }

    public void cambiarSonido() {
        setSonidoActivado(!sonidoActivado);
    }

    public void setSonidoActivado(boolean sonidoActivado) {
        this.sonidoActivado = sonidoActivado;

        if (backgroundMusic != null) {
            if (sonidoActivado) {
                backgroundMusic.play();
            } else {
                backgroundMusic.pause();
            }
        }
    }

    public String getDificultad() {
        return dificultad;
    }

    public void cambiarDificultad() {
        if (dificultad.equals("FACIL")) {
            dificultad = "NORMAL";
        } else if (dificultad.equals("NORMAL")) {
            dificultad = "DIFICIL";
        } else {
            dificultad = "FACIL";
        }
    }

    @Override
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }

        super.dispose();
    }
}
