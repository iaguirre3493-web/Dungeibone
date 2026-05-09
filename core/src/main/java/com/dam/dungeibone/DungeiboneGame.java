package com.dam.dungeibone;

import com.badlogic.gdx.Game;

public class DungeiboneGame extends Game {

    private boolean sonidoActivado;
    private String dificultad;

    @Override
    public void create() {
        sonidoActivado = true;
        dificultad = "NORMAL";
        setScreen(new FirstScreen(this));
    }

    public boolean isSonidoActivado() {
        return sonidoActivado;
    }

    public void cambiarSonido() {
        sonidoActivado = !sonidoActivado;
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

    public void setSonidoActivado(boolean sonidoActivado) {
        this.sonidoActivado = sonidoActivado;
    }
}
