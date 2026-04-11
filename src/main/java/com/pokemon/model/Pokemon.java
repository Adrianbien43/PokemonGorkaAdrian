package com.pokemon.model;

import java.util.List;
import java.util.Objects;

public class Pokemon {
    private int id;
    private String nombre;
    private List<TipoPokemon> tipoPokemon;
    private int nivel;
    private double peso;
    private double altura;
    private String descripcion;
    private boolean capturado;

    public Pokemon() {}

    public Pokemon(int id, String nombre, List<TipoPokemon> tipoPokemon,
                   int nivel, double peso, double altura, String descripcion, boolean capturado) {
        this.id = id;
        this.nombre = nombre;
        this.tipoPokemon = tipoPokemon;
        this.nivel = nivel;
        this.peso = peso;
        this.altura = altura;
        this.descripcion = descripcion;
        this.capturado = capturado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<TipoPokemon> getTipoPokemon() { return tipoPokemon; }
    public void setTipoPokemon(List<TipoPokemon> tipoPokemon) { this.tipoPokemon = tipoPokemon; }

    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isCapturado() { return capturado; }
    public void setCapturado(boolean capturado) { this.capturado = capturado; }

    @Override
    public String toString() {
        return id + " - " + nombre + " - " + tipoPokemon + " - Nivel " + nivel + " - Peso " + peso + "kg - Altura " + altura + "m - " + (capturado ? "Capturado" : "Salvaje") + " - " + descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
