package com.pokemon.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pokemon.model.Pokedex;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final String RUTA_ARCHIVO = "pokedex.json";
    private final Gson gson;

    public FileManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Pokedex cargarPokedex() {
        Path path = Paths.get(RUTA_ARCHIVO);

        if (!Files.exists(path)) {
            System.out.println("No existe archivo de datos. Creando nueva Pokedex...");
            return new Pokedex();
        }

        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            Pokedex pokedex = gson.fromJson(reader, Pokedex.class);
            if (pokedex == null) {
                return new Pokedex();
            }
            int maxId = pokedex.getPokemons().stream()
                    .mapToInt(p -> p.getId())
                    .max()
                    .orElse(0);
            pokedex.setUltimoId(maxId);
            return pokedex;
        } catch (IOException e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return new Pokedex();
        }
    }

    public void guardarPokedex(Pokedex pokedex) {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(pokedex, writer);
            System.out.println("Datos guardados.");
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }
}