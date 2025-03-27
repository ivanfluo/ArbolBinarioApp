package com.example.arbolbinario_patronesdisenio;

import java.util.List;

public interface EstrategiaRecorrido<T extends Comparable<T>> {
    void recorrer(Nodo<T> nodo);

    void recorrerYGuardar(Nodo<T> nodo, List<T> valores);  // Guardar los valores en una lista

}
