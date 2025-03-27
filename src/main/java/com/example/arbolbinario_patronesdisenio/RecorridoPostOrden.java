package com.example.arbolbinario_patronesdisenio;

public class RecorridoPostOrden implements  EstrategiaRecorrido{
    public void recorrer(Nodo nodo){
        if(nodo !=null ){
            recorrer(nodo.izquierda);
            recorrer(nodo.derecha);
            System.out.println(nodo.valor + " ");
        }
    }
}
