package com.pokemon.model;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
    private List<Pokemon> pokemons;
    private int ultimoId;

    public Pokedex() {
        this.pokemons = new ArrayList<>();
        this.ultimoId = 0;
    }

    public List<Pokemon> getPokemons() { return pokemons; }
    public void setPokemons(List<Pokemon> pokemons) { this.pokemons = pokemons; }

    public int getUltimoId() { return ultimoId; }
    public void setUltimoId(int ultimoId) { this.ultimoId = ultimoId; }

    public int generarNuevoId() {
        return ++ultimoId;
    }
}