package com.example.arbolbinario_patronesdisenio;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la ruta del archivo de texto:");
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
            System.out.println("\n");

            System.out.println("¿Desea eliminar un nodo? (S/N)");
            char respuesta = scanner.next().toUpperCase().charAt(0);
            if (respuesta == 'S') {
                System.out.println("Ingrese el valor a eliminar:");
                String valorEliminar = scanner.next();
                try {
                    if (arbol.raiz.valor instanceof Integer) {
                        arbol.eliminar((T) Integer.valueOf(valorEliminar));
                    } else {
                        arbol.eliminar((T) valorEliminar);
                    }
                    System.out.println("Nodo eliminado. Nuevo recorrido:");
                    arbol.recorrer(estrategia);
                } catch (Exception e) {
                    System.out.println("Error al eliminar el nodo: " + e.getMessage());
                }
            }

            System.out.println("¿Desea exportar el recorrido a un archivo? (S/N)");
            char exportarRespuesta = scanner.next().toUpperCase().charAt(0);
            if (exportarRespuesta == 'S') {
                List<T> valoresRecorrido = arbol.obtenerValoresRecorrido(estrategia);
                ManejadorArchivos.exportarRecorrido(estrategia.getClass().getSimpleName(), valoresRecorrido);
            }
        }
    }
}
