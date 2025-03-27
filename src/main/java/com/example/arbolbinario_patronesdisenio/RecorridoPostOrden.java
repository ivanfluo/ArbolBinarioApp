package com.example.arbolbinario_patronesdisenio;

import java.util.List;

public class RecorridoPostOrden <T extends Comparable<T>> implements  EstrategiaRecorrido<T>{
    public void recorrer(Nodo<T> nodo){
        if(nodo !=null ){
            recorrer(nodo.izquierda);
            recorrer(nodo.derecha);
            System.out.println(nodo.valor + " ");
        }
    }

    @Override
    public void recorrerYGuardar(Nodo<T> nodo, List<T> valores) {
        if (nodo != null) {
            recorrerYGuardar(nodo.izquierda, valores);
            recorrerYGuardar(nodo.derecha, valores);
            valores.add(nodo.valor);
        }
    }
}
