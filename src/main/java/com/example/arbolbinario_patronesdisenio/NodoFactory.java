package com.example.arbolbinario_patronesdisenio;

public class NodoFactory {
    public static <T extends  Comparable<T>> Nodo<T> crearNodo(T valor){
        return new Nodo<>(valor);
    }
}
