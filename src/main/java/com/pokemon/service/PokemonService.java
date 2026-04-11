package com.pokemon.service;

import com.pokemon.model.Pokedex;
import com.pokemon.model.Pokemon;
import com.pokemon.model.TipoPokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PokemonService {
    private Pokedex pokedex;
    private final FileManager fileManager;
    private final Scanner scanner;

    public PokemonService() {
        this.fileManager = new FileManager();
        this.scanner = new Scanner(System.in);
        cargarDatos();
    }

    private void cargarDatos() {
        this.pokedex = fileManager.cargarPokedex();
        System.out.println("Cargados " + pokedex.getPokemons().size() + " Pokemon");
    }

    public void guardarDatos() {
        fileManager.guardarPokedex(pokedex);
    }

    public void agregarPokemon() {
        System.out.println("\n--- ANADIR NUEVO POKEMON ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacio");
            return;
        }

        List<TipoPokemon> tipos = seleccionarTipos();

        System.out.print("Nivel (1-100): ");
        int nivel = leerEntero(1, 100);

        System.out.print("Peso (kg): ");
        double peso = leerDouble();

        System.out.print("Altura (m): ");
        double altura = leerDouble();

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine().trim();

        System.out.print("Capturado? (s/n): ");
        boolean capturado = scanner.nextLine().trim().equalsIgnoreCase("s");

        Pokemon nuevo = new Pokemon(
                pokedex.generarNuevoId(),
                nombre,
                tipos,
                nivel,
                peso,
                altura,
                descripcion,
                capturado
        );

        pokedex.getPokemons().add(nuevo);
        guardarDatos();
        System.out.println("Pokemon anadido con ID: " + nuevo.getId());
    }

    public void modificarPokemon() {
        System.out.println("\n--- MODIFICAR POKEMON ---");
        listarTodos();

        System.out.print("\nID del Pokemon a modificar: ");
        int id = leerEntero();

        Pokemon pokemon = buscarPorId(id);
        if (pokemon == null) {
            System.out.println("Pokemon no encontrado");
            return;
        }

        System.out.println("Seleccionado: " + pokemon.getNombre());
        System.out.println("1. Nombre");
        System.out.println("2. Tipos");
        System.out.println("3. Nivel");
        System.out.println("4. Peso");
        System.out.println("5. Altura");
        System.out.println("6. Descripcion");
        System.out.println("7. Estado de captura");
        System.out.print("Opcion: ");

        int opcion = leerEntero(1, 7);

        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                pokemon.setNombre(scanner.nextLine().trim());
                break;
            case 2:
                pokemon.setTipoPokemon(seleccionarTipos());
                break;
            case 3:
                System.out.print("Nuevo nivel (1-100): ");
                pokemon.setNivel(leerEntero(1, 100));
                break;
            case 4:
                System.out.print("Nuevo peso: ");
                pokemon.setPeso(leerDouble());
                break;
            case 5:
                System.out.print("Nueva altura: ");
                pokemon.setAltura(leerDouble());
                break;
            case 6:
                System.out.print("Nueva descripcion: ");
                pokemon.setDescripcion(scanner.nextLine().trim());
                break;
            case 7:
                pokemon.setCapturado(!pokemon.isCapturado());
                System.out.println("Estado cambiado a: " +
                        (pokemon.isCapturado() ? "Capturado" : "Salvaje"));
                break;
        }

        guardarDatos();
        System.out.println("Pokemon actualizado");
    }

    public void eliminarPokemon() {
        System.out.println("\n--- ELIMINAR POKEMON ---");
        listarTodos();

        System.out.print("\nID del Pokemon a eliminar: ");
        int id = leerEntero();

        Pokemon pokemon = buscarPorId(id);
        if (pokemon == null) {
            System.out.println("Pokemon no encontrado");
            return;
        }

        System.out.print("Eliminar " + pokemon.getNombre() + "? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            pokedex.getPokemons().remove(pokemon);
            guardarDatos();
            System.out.println("Pokemon eliminado");
        } else {
            System.out.println("Operacion cancelada");
        }
    }

    public void buscarPorNombre() {
        System.out.print("\nNombre a buscar: ");
        String nombre = scanner.nextLine().trim().toLowerCase();

        List<Pokemon> resultados = pokedex.getPokemons().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre))
                .collect(Collectors.toList());

        mostrarResultados(resultados, "Busqueda por nombre: " + nombre);
    }

    public void buscarPorTipo() {
        System.out.println("\nTipos disponibles:");
        for (TipoPokemon tipo : TipoPokemon.values()) {
            System.out.println((tipo.ordinal() + 1) + ". " + tipo);
        }
        System.out.print("Selecciona tipo: ");

        int opcion = leerEntero(1, TipoPokemon.values().length);
        TipoPokemon tipoSeleccionado = TipoPokemon.values()[opcion - 1];

        List<Pokemon> resultados = pokedex.getPokemons().stream()
                .filter(p -> p.getTipoPokemon().contains(tipoSeleccionado))
                .collect(Collectors.toList());

        mostrarResultados(resultados, "Pokemon tipo " + tipoSeleccionado);
    }

    public void buscarCapturados() {
        List<Pokemon> resultados = pokedex.getPokemons().stream()
                .filter(Pokemon::isCapturado)
                .collect(Collectors.toList());

        mostrarResultados(resultados, "Pokemon Capturados");
    }

    public void buscarPorNivel() {
        System.out.print("Nivel minimo: ");
        int min = leerEntero(1, 100);
        System.out.print("Nivel maximo: ");
        int max = leerEntero(min, 100);

        List<Pokemon> resultados = pokedex.getPokemons().stream()
                .filter(p -> p.getNivel() >= min && p.getNivel() <= max)
                .collect(Collectors.toList());

        mostrarResultados(resultados, "Pokemon nivel " + min + "-" + max);
    }

    public void listarTodos() {
        mostrarResultados(pokedex.getPokemons(), "TODOS LOS POKEMON");
    }

    public void mostrarEstadisticas() {
        long total = pokedex.getPokemons().size();
        long capturados = pokedex.getPokemons().stream()
                .filter(Pokemon::isCapturado).count();

        System.out.println("\n--- ESTADISTICAS ---");
        System.out.println("Total Pokemon: " + total);
        System.out.println("Capturados: " + capturados);
        System.out.println("Por capturar: " + (total - capturados));

        if (total > 0) {
            double nivelMedio = pokedex.getPokemons().stream()
                    .mapToInt(Pokemon::getNivel)
                    .average()
                    .orElse(0);
            System.out.printf("Nivel medio: %.1f%n", nivelMedio);
        }
    }

    private List<TipoPokemon> seleccionarTipos() {
        List<TipoPokemon> tipos = new ArrayList<>();

        System.out.println("\nTipos disponibles:");
        TipoPokemon[] valores = TipoPokemon.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }

        System.out.print("Selecciona tipo principal (numero): ");
        int tipo1 = leerEntero(1, valores.length);
        tipos.add(valores[tipo1 - 1]);

        System.out.print("Anadir tipo secundario? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("Selecciona tipo secundario: ");
            int tipo2 = leerEntero(1, valores.length);
            if (tipo2 != tipo1) {
                tipos.add(valores[tipo2 - 1]);
            }
        }

        return tipos;
    }

    private Pokemon buscarPorId(int id) {
        return pokedex.getPokemons().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void mostrarResultados(List<Pokemon> lista, String titulo) {
        System.out.println("\n=== " + titulo + " ===");

        if (lista.isEmpty()) {
            System.out.println("No se encontraron resultados");
        } else {
            lista.forEach(System.out::println);
            System.out.println("\nTotal: " + lista.size() + " Pokemon");
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero valido: ");
            }
        }
    }

    private int leerEntero(int min, int max) {
        while (true) {
            int valor = leerEntero();
            if (valor >= min && valor <= max) return valor;
            System.out.printf("Debe estar entre %d y %d: ", min, max);
        }
    }

    private double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero valido: ");
            }
        }
    }
}