package com.example.arbolbinario_patronesdisenio;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ArbolBinarioApp extends Application {

    private ArbolBinario<String> arbol = new ArbolBinario<>();
    private Pane pane;
    private String nodoSeleccionado = null;

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
        MenuItem eliminarNodo = new MenuItem("Eliminar nodo");
        MenuItem seleccionarRecorrido = new MenuItem("Seleccionar Recorrido");
        MenuItem exportarRecorrido = new MenuItem("Exportar Recorrido");

        menuOpciones.getItems().addAll(cargarArchivo, insertarValor, eliminarNodo, seleccionarRecorrido, exportarRecorrido);
        menuBar.getMenus().add(menuOpciones);
        root.setTop(menuBar);

        pane = new Pane();
        root.setCenter(pane);

        // Configurar eventos
        cargarArchivo.setOnAction(e -> cargarDesdeArchivo());
        insertarValor.setOnAction(e -> insertarValor());
        eliminarNodo.setOnAction(e -> eliminarValor());
        seleccionarRecorrido.setOnAction(e -> seleccionarRecorrido());
        exportarRecorrido.setOnAction(e -> exportarRecorrido());

        Scene scene = new Scene(root, 1080, 800);
        primaryStage.setTitle("Árbol Binario - Programación III UMG");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void eliminarValor() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar Nodo");
        dialog.setHeaderText("Ingrese el valor el nodo a eliminar");
        dialog.setContentText("Valor:");

        dialog.showAndWait().ifPresent(valor -> {
            if (valor.isEmpty()){
                mostrarAlerta("Error", "Debe ingresar un valor.");
                return;
            }

            arbol.eliminar(valor);
            nodoSeleccionado = null;
            dibujarArbol();
        });
    }

    private void cargarDesdeArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File archivo = fileChooser.showOpenDialog(null);

        if (archivo != null) {
            try {
                arbol = new ArbolBinario<>();
                List<String> lineas = Files.readAllLines(archivo.toPath());
                for (String linea : lineas) {
                    List<String> valores = Arrays.asList(linea.split(",|\\s+"));
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
        pane.getChildren().clear();
        dibujarNodo(arbol.raiz, pane.getWidth() / 2, 50, pane.getWidth() / 4);
    }

    private void dibujarNodo(Nodo<String> nodo, double x, double y, double offset) {
        if (nodo == null) return;

        if (nodo.izquierda != null) {
            Line lineaIzquierda = new Line(x, y, x - offset, y + 50);
            lineaIzquierda.setStroke(Color.GRAY);
            pane.getChildren().add(lineaIzquierda);
            dibujarNodo(nodo.izquierda, x - offset, y + 50, offset / 2);
        }

        if (nodo.derecha != null) {
            Line lineaDerecha = new Line(x, y, x + offset, y + 50);
            lineaDerecha.setStroke(Color.GRAY);
            pane.getChildren().add(lineaDerecha);
            dibujarNodo(nodo.derecha, x + offset, y + 50, offset / 2);
        }

        Circle circulo = new Circle(x, y, 20, nodo.valor.equals(nodoSeleccionado) ? Color.RED : Color.BLUE);
        circulo.setStroke(Color.BLACK);

        Text texto = new Text(x - 5, y + 5, nodo.valor);
        texto.setFill(Color.WHITE);

        circulo.setOnMouseClicked(e -> {
            nodoSeleccionado = nodo.valor;
            dibujarArbol();
        });

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), circulo);
        fade.setFromValue(0.5);
        fade.setToValue(1);
        fade.play();

        pane.getChildren().addAll(circulo, texto);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
