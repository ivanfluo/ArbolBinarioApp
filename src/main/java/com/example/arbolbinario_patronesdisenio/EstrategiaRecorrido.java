package com.example.arbolbinario_patronesdisenio;

public interface EstrategiaRecorrido<T extends Comparable<T>> {
    void recorrer(Nodo<T> nodo);
}
