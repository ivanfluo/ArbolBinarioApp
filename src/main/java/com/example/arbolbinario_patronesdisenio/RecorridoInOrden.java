package com.example.arbolbinario_patronesdisenio;

import java.util.List;

public class RecorridoInOrden <T extends Comparable<T>> implements EstrategiaRecorrido<T>{
    public void recorrer(Nodo<T> nodo){
        if(nodo != null){
            recorrer(nodo.izquierda);
            System.out.println(nodo.valor + " ");
            recorrer(nodo.derecha);
        }
    }

    @Override
    public void recorrerYGuardar(Nodo<T> nodo, List<T> valores) {
        if (nodo != null) {
            recorrerYGuardar(nodo.izquierda, valores);
            valores.add(nodo.valor);
            recorrerYGuardar(nodo.derecha, valores);
        }
    }
}
