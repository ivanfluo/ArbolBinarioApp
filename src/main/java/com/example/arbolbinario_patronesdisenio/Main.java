package com.example.arbolbinario_patronesdisenio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*ArbolBinario<Integer> arbolNumeros = new ArbolBinario<>();
        arbolNumeros.insertar(50);
        arbolNumeros.insertar(30);
        arbolNumeros.insertar(70);

        ArbolBinario<String> arbolLetras = new ArbolBinario<>();
        arbolLetras.insertar("A");
        arbolLetras.insertar("B");
        arbolLetras.insertar("C");

        System.out.println("Recorrido en Preorden:");
        arbolNumeros.recorrer(new RecorridoPreOrden());
        System.out.println();

        System.out.println("Recorrido en Inorden:");
        arbolNumeros.recorrer(new RecorridoInOrden());
        System.out.println();

        System.out.println("Recorrido en Postorden:");
        arbolNumeros.recorrer(new RecorridoPostOrden());
        System.out.println();

        System.out.println(" ---------------------------------------------------- ");

        System.out.println("Recorrido en Preorden:");
        arbolLetras.recorrer(new RecorridoPreOrden());
        System.out.println();

        System.out.println("Recorrido en Inorden:");
        arbolLetras.recorrer(new RecorridoInOrden());
        System.out.println();

        System.out.println("Recorrido en Postorden:");
        arbolLetras.recorrer(new RecorridoPostOrden());
        System.out.println();*/


        /*ArbolBinario<Integer> arbol = new ArbolBinario<>();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        System.out.println("Recorrido en Inorden antes de eliminar:");
        arbol.recorrer(new RecorridoInOrden());

        // Eliminamos la raíz
        arbol.eliminar(50);

        System.out.println("Recorrido en Inorden después de eliminar:");
        arbol.recorrer(new RecorridoInOrden());*/

        /*ArbolBinario<Integer> arbol = new ArbolBinario<>();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la ruta del archivo CSV:");
        String rutaArchivo = scanner.nextLine();

        // Leer e insertar valores del archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                for (String valor : valores) {
                    arbol.insertar(Integer.parseInt(valor.trim()));
                }
            }
            System.out.println("Valores insertados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Elegir tipo de recorrido
        System.out.println("Seleccione el tipo de recorrido:");
        System.out.println("1. Preorden");
        System.out.println("2. Inorden");
        System.out.println("3. Postorden");

        int opcion = scanner.nextInt();
        EstrategiaRecorrido<Integer> estrategia = switch (opcion) {
            case 1 -> new RecorridoPreOrden();
            case 2 -> new RecorridoInOrden();
            case 3 -> new RecorridoPostOrden();
            default -> {
                System.out.println("Opción no válida.");
                yield null;
            }
        };

        if (estrategia != null) {
            System.out.println("Recorrido seleccionado:");
            arbol.recorrer(estrategia);
        }*/

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la ruta del archivo CSV:");
        String rutaArchivo = scanner.nextLine();

        List<String> valores = ManejadorArchivos.leerArchivo(rutaArchivo);

        if (valores.isEmpty()) {
            System.out.println("El archivo está vacío o no se pudo leer.");
            return;
        }

        if (ManejadorArchivos.esNumerico(valores)) {
            ArbolBinario<Integer> arbolNumeros = new ArbolBinario<>();
            for (String valor : valores) {
                arbolNumeros.insertar(Integer.parseInt(valor));
            }
            ejecutarRecorrido(arbolNumeros);
        } else {
            ArbolBinario<String> arbolPalabras = new ArbolBinario<>();
            for (String valor : valores) {
                arbolPalabras.insertar(valor);
            }
            ejecutarRecorrido(arbolPalabras);
        }
    }

    private static <T extends Comparable<T>> void ejecutarRecorrido(ArbolBinario<T> arbol) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de recorrido:");
        System.out.println("1. Preorden");
        System.out.println("2. Inorden");
        System.out.println("3. Postorden");

        int opcion = scanner.nextInt();
        EstrategiaRecorrido<T> estrategia = switch (opcion) {
            case 1 -> new RecorridoPreOrden();
            case 2 -> new RecorridoInOrden();
            case 3 -> new RecorridoPostOrden();
            default -> {
                System.out.println("Opción no válida.");
                yield null;
            }
        };

        if (estrategia != null) {
            System.out.println("Recorrido seleccionado:");
            arbol.recorrer(estrategia);
        }

    }
}
