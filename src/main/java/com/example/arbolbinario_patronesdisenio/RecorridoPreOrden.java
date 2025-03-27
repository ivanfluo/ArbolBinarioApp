package com.example.arbolbinario_patronesdisenio;

public class RecorridoPreOrden implements EstrategiaRecorrido{
    public void recorrer(Nodo nodo){
        if(nodo != null){
            System.out.println(nodo.valor + " ");
            recorrer(nodo.izquierda);
            recorrer(nodo.derecha);
        }
    }
}
