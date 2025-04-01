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
import java.util.ArrayList;
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
        MenuItem ejecutarRecorrido = new MenuItem("Ejecutar Recorrido");
        MenuItem exportarRecorrido = new MenuItem("Exportar Recorrido");

        menuOpciones.getItems().addAll(cargarArchivo, insertarValor, eliminarNodo, ejecutarRecorrido, exportarRecorrido);
        menuBar.getMenus().add(menuOpciones);
        root.setTop(menuBar);

        pane = new Pane();
        root.setCenter(pane);

        // Configurar eventos
        cargarArchivo.setOnAction(e -> cargarDesdeArchivo());
        insertarValor.setOnAction(e -> insertarValor());
        eliminarNodo.setOnAction(e -> eliminarValor());
        ejecutarRecorrido.setOnAction(e -> ejecutarRecorrido());
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

    private void ejecutarRecorrido() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Selecciona una opción", "PreOrden", "InOrden", "PostOrden", "Todos");
        dialog.setTitle("Seleccionar Recorrido");
        dialog.setHeaderText("Elija el tipo de recorrido");
        dialog.setContentText("Tipo:");

        dialog.showAndWait().ifPresent(tipoRecorrido -> {
            List<String> valores;
            EstrategiaRecorrido<String> estrategia;

            if ("PreOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                estrategia = new RecorridoPreOrden<>();
                valores = arbol.obtenerValoresRecorrido(estrategia);
                simularRecorrido(valores, "Recorrido PreOrden");
            }

            if ("InOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                estrategia = new RecorridoInOrden<>();
                valores = arbol.obtenerValoresRecorrido(estrategia);
                simularRecorrido(valores, "Recorrido InOrden");
            }

            if ("PostOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                estrategia = new RecorridoPostOrden<>();
                valores = arbol.obtenerValoresRecorrido(estrategia);
                simularRecorrido(valores, "Recorrido PostOrden");
            }
        });
    }

    private void simularRecorrido(List<String> valores, String titulo) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(100);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.getDialogPane().setContent(textArea);
        alerta.show();

        new Thread(() -> {
            StringBuilder recorrido = new StringBuilder();
            for (String valor : valores) {
                recorrido.append(valor).append(", ");
                String textoMostrado = recorrido.toString();

                javafx.application.Platform.runLater(() -> textArea.setText(textoMostrado));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    private void exportarRecorrido() {
        if (arbol.raiz == null) {
            mostrarAlerta("Error", "No hay recorridos disponibles para exportar.");
            return;
        }

        ChoiceDialog<String> dialogo = new ChoiceDialog<>("Todos", "PreOrden", "InOrden", "PostOrden", "Todos");
        dialogo.setTitle("Exportar Recorrido");
        dialogo.setHeaderText("Elija el tipo de recorrido a exportar");
        dialogo.setContentText("Tipo:");

        dialogo.showAndWait().ifPresent(tipoRecorrido -> {
            List<String> valores;
            if ("PreOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                valores = arbol.obtenerValoresRecorrido(new RecorridoPreOrden<>());
                ManejadorArchivos.exportarRecorrido("PreOrden", valores);
            }
            if ("InOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                valores = arbol.obtenerValoresRecorrido(new RecorridoInOrden<>());
                ManejadorArchivos.exportarRecorrido("InOrden", valores);
            }
            if ("PostOrden".equals(tipoRecorrido) || "Todos".equals(tipoRecorrido)) {
                valores = arbol.obtenerValoresRecorrido(new RecorridoPostOrden<>());
                ManejadorArchivos.exportarRecorrido("PostOrden", valores);
            }
            mostrarAlerta("Éxito", "Recorrido exportado exitosamente.");
        });
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
