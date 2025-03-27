package com.example.arbolbinario_patronesdisenio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {
    public static List<String> leerArchivo(String rutaArchivo){
        List<String> lista = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea = br.readLine();

            if(linea != null){
                String[] valores = linea.split(",");

                for(String valor : valores){
                    lista.add(valor.trim());
                }
            }
        }catch(IOException e){
            System.out.println("Error al leer el archvio: " + e.getMessage());
        }catch(NumberFormatException e){
            System.out.println("Error: El archivo contiene datos no numéricos");
        }
        return lista;
    }

    public static boolean esNumerico(List<String> valores){
        for(String valor: valores){
            if(!valor.matches("-?\\d+")){
                return false;
            }
        }
        return true;
    }

    public static <T> void exportarRecorrido(String tipoRecorrido, List<T> valores){
        String rutaArchivo = tipoRecorrido + ".txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))){
            writer.write(tipoRecorrido);
            writer.newLine();
            writer.write(String.join(", ", valores.toString().replaceAll("[\\[\\]]", "")));
            System.out.println("Recorrido exportado a " + rutaArchivo);
        }catch(IOException e){
            System.out.println("Error al exportar el archivo: " + e.getMessage());
        }
    }
}
