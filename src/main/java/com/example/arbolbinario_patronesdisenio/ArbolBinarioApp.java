package com.example.arbolbinario_patronesdisenio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ArbolBinarioApp extends Application {

    private ArbolBinario<String> arbol = new ArbolBinario<>();
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menuOpciones = new Menu("Opciones");
        MenuItem cargarArchivo = new MenuItem("Cargar Archivo");
        MenuItem insertarValor = new MenuItem("Insertar Valor");
        MenuItem seleccionarRecorrido = new MenuItem("Seleccionar Recorrido");
        MenuItem exportarRecorrido = new MenuItem("Exportar Recorrido");

        menuOpciones.getItems().addAll(cargarArchivo, insertarValor, seleccionarRecorrido, exportarRecorrido);
        menuBar.getMenus().add(menuOpciones);
        root.setTop(menuBar);

        canvas = new Canvas(800, 600);
        root.setCenter(canvas);

        cargarArchivo.setOnAction(e -> cargarDesdeArchivo());
        insertarValor.setOnAction(e -> insertarValor());
        seleccionarRecorrido.setOnAction(e -> seleccionarRecorrido());
        exportarRecorrido.setOnAction(e -> exportarRecorrido());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Árbol Binario - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cargarDesdeArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File archivo = fileChooser.showOpenDialog(null);

        if (archivo != null) {
            try {
                List<String> lineas = Files.readAllLines(archivo.toPath());
                for (String linea : lineas) {
                    List<String> valores = Arrays.asList(linea.split(",|\s+"));
                    for (String valor : valores) {
                        if (!valor.isEmpty()) {
                            arbol.insertar(valor.trim());
                        }
                    }
                }
                dibujarArbol();
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo leer el archivo.");
            }
        }
    }

    private void insertarValor() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Insertar Valor");
        dialog.setHeaderText("Ingrese un valor para insertar en el árbol:");
        dialog.setContentText("Valor:");
        dialog.showAndWait().ifPresent(valor -> {
            arbol.insertar(valor);
            dibujarArbol();
        });
    }

    private void seleccionarRecorrido() {
        // Implementación para seleccionar tipo de recorrido
    }

    private void exportarRecorrido() {
        // Implementación para exportar recorrido
    }

    private void dibujarArbol() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        dibujarNodo(gc, arbol.raiz, canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void dibujarNodo(GraphicsContext gc, Nodo<String> nodo, double x, double y, double offset) {
        if (nodo == null) return;
        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 20, y - 20, 40, 40);
        gc.setFill(Color.BLACK);
        gc.fillText(nodo.valor, x - 10, y + 5);

        if (nodo.izquierda != null) {
            gc.strokeLine(x, y, x - offset, y + 50);
            dibujarNodo(gc, nodo.izquierda, x - offset, y + 50, offset / 2);
        }
        if (nodo.derecha != null) {
            gc.strokeLine(x, y, x + offset, y + 50);
            dibujarNodo(gc, nodo.derecha, x + offset, y + 50, offset / 2);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
