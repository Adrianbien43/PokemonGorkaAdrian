package com.pokemon.ui;

import com.pokemon.service.PokemonService;
import java.util.Scanner;

public class PokedexUI {
    private final Scanner scanner;
    private final PokemonService pokemonService;

    public PokedexUI() {
        this.scanner = new Scanner(System.in);
        this.pokemonService = new PokemonService();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);

        System.out.println("Saliendo del sistema. Datos guardados.");
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n=== POKEDEX ===");
        System.out.println("1. Agregar Pokemon");
        System.out.println("2. Modificar Pokemon");
        System.out.println("3. Eliminar Pokemon");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Buscar por tipo");
        System.out.println("6. Ver capturados");
        System.out.println("7. Buscar por nivel");
        System.out.println("8. Listar todos");
        System.out.println("9. Estadisticas");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                pokemonService.agregarPokemon();
                break;
            case 2:
                pokemonService.modificarPokemon();
                break;
            case 3:
                pokemonService.eliminarPokemon();
                break;
            case 4:
                pokemonService.buscarPorNombre();
                break;
            case 5:
                pokemonService.buscarPorTipo();
                break;
            case 6:
                pokemonService.buscarCapturados();
                break;
            case 7:
                pokemonService.buscarPorNivel();
                break;
            case 8:
                pokemonService.listarTodos();
                break;
            case 9:
                pokemonService.mostrarEstadisticas();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion invalida. Intente de nuevo.");
        }
    }
}