package com.example.arbolbinario_patronesdisenio;

import java.util.List;

public class RecorridoPreOrden<T extends Comparable<T>> implements EstrategiaRecorrido<T> {

    @Override
    public void recorrer(Nodo<T> nodo) {
        if (nodo != null) {
            System.out.println(nodo.valor + " ");
            recorrer(nodo.izquierda);
            recorrer(nodo.derecha);
        }
    }

    @Override
    public void recorrerYGuardar(Nodo<T> nodo, List<T> valores) {
        if (nodo != null) {
            valores.add(nodo.valor);
            recorrerYGuardar(nodo.izquierda, valores);
            recorrerYGuardar(nodo.derecha, valores);
        }
    }
}