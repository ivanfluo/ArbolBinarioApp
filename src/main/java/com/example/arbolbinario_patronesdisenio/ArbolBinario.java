package com.example.arbolbinario_patronesdisenio;

import java.util.ArrayList;
import java.util.List;

public class ArbolBinario<T extends  Comparable<T>> {
    Nodo<T> raiz;

    public ArbolBinario(){
        this.raiz = null;
    }

    public void insertar(T valor){
        raiz = insertarRecursivo(raiz, valor);
    }

    public void eliminar(T valor){
        raiz = eliminarRecursivo(raiz, valor);
    }

    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T valor){
        if(nodo == null){
            return NodoFactory.crearNodo(valor);
        }

        if(valor.compareTo(nodo.valor) < 0){
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        }else if (valor.compareTo(nodo.valor) > 0){
            nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        }

        return nodo;
    }

    private Nodo<T> eliminarRecursivo(Nodo<T> nodo, T valor){
        if (nodo == null){
            return null;
        }

        if(valor.compareTo(nodo.valor) < 0){
            nodo.izquierda = eliminarRecursivo(nodo.izquierda, valor);
        }else if(valor.compareTo(nodo.valor) > 0){
            nodo.derecha = eliminarRecursivo(nodo.derecha, valor);
        }else{
            // Caso 1: Nodo sin hijos
            if(nodo.izquierda == null && nodo.derecha == null){
                return null;
            }
            // Caso 2: Nodo con un hijo
            else if (nodo.izquierda == null) {
                return nodo.derecha;
            }else if (nodo.derecha == null){
                return nodo.izquierda;
            }

            // Caso 3: Nodo con dos hijos
            else {
                Nodo<T> sucesor = encontrarMinimo(nodo.derecha);
                nodo.valor = sucesor.valor;
                nodo.derecha = eliminarRecursivo(nodo.derecha, sucesor.valor);
            }
        }
        return nodo;
    }

    private Nodo<T> encontrarMinimo(Nodo<T> nodo){
        while (nodo.izquierda != null){
            nodo = nodo.izquierda;
        }
        return nodo;
    }

    public void recorrer(EstrategiaRecorrido<T> estrategiaRecorrido){
        estrategiaRecorrido.recorrer(raiz);
    }

    public List<T> obtenerValoresRecorrido(EstrategiaRecorrido<T> estrategia) {
        List<T> valores = new ArrayList<>();
        recorrerYGuardar(raiz, estrategia, valores);
        return valores;
    }

    private void recorrerYGuardar(Nodo<T> nodo, EstrategiaRecorrido<T> estrategia, List<T> valores) {
        if (nodo != null) {
            estrategia.recorrerYGuardar(nodo, valores);
        }
    }

}