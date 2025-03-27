package com.example.arbolbinario_patronesdisenio;

public class RecorridoInOrden implements EstrategiaRecorrido{
    public void recorrer(Nodo nodo){
        if(nodo != null){
            recorrer(nodo.izquierda);
            System.out.println(nodo.valor + " ");
            recorrer(nodo.derecha);
        }
    }
}
