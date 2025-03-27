package com.example.arbolbinario_patronesdisenio;

public class Nodo <T extends  Comparable<T>> {
    T valor;
    Nodo<T> izquierda;
    Nodo<T> derecha;

    public Nodo(T valor){
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
    }
}