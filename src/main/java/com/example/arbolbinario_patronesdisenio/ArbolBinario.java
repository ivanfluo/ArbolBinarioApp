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

    private Nodo<T> insertarRecursivo(Nodo<T> nodo, T valor) {
        if (nodo == null) {
            return NodoFactory.crearNodo(valor);
        }

        // Si son numéricos, comparar como números
        if (esNumerico(valor.toString()) && esNumerico(nodo.valor.toString())) {
            int numValor = Integer.parseInt(valor.toString());
            int numNodo = Integer.parseInt(nodo.valor.toString());
            if (numValor < numNodo) {
                nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
            } else if (numValor > numNodo) {
                nodo.derecha = insertarRecursivo(nodo.derecha, valor);
            }
        } else {
            // Comparación normal (Strings)
            if (valor.compareTo(nodo.valor) < 0) {
                nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
            } else if (valor.compareTo(nodo.valor) > 0) {
                nodo.derecha = insertarRecursivo(nodo.derecha, valor);
            }
        }
        return nodo;
    }

    private boolean esNumerico(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
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

    public int buscarAlturaNodo(T valor){
        return buscarAlturaNodo(raiz, valor, 1);
    }

    private int buscarAlturaNodo(Nodo<T> nodo, T valor, int altura) {
        if (nodo == null) {
            return -1;
        }

        if (nodo.valor.equals(valor)) {
            return altura;
        }

        // Busqueda en subárbol izquierdo
        int alturaIzquierda = buscarAlturaNodo(nodo.izquierda, valor, altura + 1);
        if (alturaIzquierda != -1) {
            return alturaIzquierda;
        }

        return buscarAlturaNodo(nodo.derecha, valor, altura + 1);
    }

}