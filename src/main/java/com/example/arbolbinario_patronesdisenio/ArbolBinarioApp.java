package com.example.arbolbinario_patronesdisenio;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Canvas canvas;
    private String nodoSeleccionado = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Estilo CSS moderno
        String css = """
            .menu-bar {
                -fx-background-color: #4a6fa5;
                -fx-text-fill: white;
            }
            .menu-item {
                -fx-background-color: #f5f7fa;
            }
            .menu-item:hover {
                -fx-background-color: #dbe2ef;
            }
            """;

        MenuBar menuBar = new MenuBar();
        Menu menuOpciones = new Menu("Opciones");
        MenuItem cargarArchivo = new MenuItem("Cargar Archivo");
        MenuItem insertarValor = new MenuItem("Insertar Valor");
        MenuItem seleccionarRecorrido = new MenuItem("Seleccionar Recorrido");
        MenuItem exportarRecorrido = new MenuItem("Exportar Recorrido");

        menuOpciones.getItems().addAll(cargarArchivo, insertarValor, seleccionarRecorrido, exportarRecorrido);
        menuBar.getMenus().add(menuOpciones);
        root.setTop(menuBar);

        canvas = new Canvas(1080, 800);
        root.setCenter(canvas);

        // Configurar eventos
        cargarArchivo.setOnAction(e -> cargarDesdeArchivo());
        insertarValor.setOnAction(e -> insertarValor());
        seleccionarRecorrido.setOnAction(e -> seleccionarRecorrido());
        exportarRecorrido.setOnAction(e -> exportarRecorrido());

        // Interactividad con clics
        canvas.setOnMouseClicked(e -> {
            buscarNodoEnPosicion(arbol.raiz, e.getX(), e.getY(),
                    canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
            dibujarArbol();
        });

        Scene scene = new Scene(root, 1080, 800);
        scene.getStylesheets().add("data:text/css," + css);
        primaryStage.setTitle("Árbol Binario - Visualización Moderna");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Tooltip informativo
        Tooltip.install(canvas, new Tooltip("Haz clic en un nodo para seleccionarlo"));
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
        dialog.getDialogPane().setStyle("-fx-background-color: #f5f7fa;");

        dialog.showAndWait().ifPresent(valor -> {
            arbol.insertar(valor);

            // Animación de inserción
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(canvas.opacityProperty(), 0.7)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(canvas.opacityProperty(), 1))
            );
            timeline.play();

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

        // Fondo
        gc.setFill(Color.web("#f5f7fa"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        dibujarNodo(gc, arbol.raiz, canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
    }

    private void dibujarNodo(GraphicsContext gc, Nodo<String> nodo, double x, double y, double offset) {
        if (nodo == null) return;

        // Resaltar si está seleccionado
        if (nodo.valor.equals(nodoSeleccionado)) {
            gc.setFill(Color.web("#e74c3c"));
        } else {
            gc.setFill(Color.web("#4a6fa5"));
        }

        // Dibujar nodo
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.setStroke(Color.web("#2c3e50"));
        gc.setLineWidth(2);
        gc.strokeOval(x - 20, y - 20, 40, 40);

        // Texto del nodo centrado
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Centrar texto mejor en círculo
        double textWidth = nodo.valor.length() * gc.getFont().getSize() / 3;
        double textX = x - textWidth / 2;
        double textY = y + 5;
        gc.fillText(nodo.valor, textX, textY);

        // Conexiones
        if (nodo.izquierda != null) {
            gc.setStroke(Color.web("#7f8c8d"));
            gc.setLineWidth(1.5);
            gc.strokeLine(x, y + 20, x - offset, y + 50 - 20);
            dibujarNodo(gc, nodo.izquierda, x - offset, y + 50, offset / 2);
        }
        if (nodo.derecha != null) {
            gc.setStroke(Color.web("#7f8c8d"));
            gc.setLineWidth(1.5);
            gc.strokeLine(x, y + 20, x + offset, y + 50 - 20);
            dibujarNodo(gc, nodo.derecha, x + offset, y + 50, offset / 2);
        }
    }

    private void buscarNodoEnPosicion(Nodo<String> nodo, double targetX, double targetY,
                                      double x, double y, double offset) {
        if (nodo == null) return;

        // Verificar si el clic está dentro del área del nodo
        if (targetX >= x - 25 && targetX <= x + 25 &&
                targetY >= y - 20 && targetY <= y + 20) {
            nodoSeleccionado = nodo.valor;
            return;
        }

        // Buscar en los hijos
        if (nodo.izquierda != null) {
            buscarNodoEnPosicion(nodo.izquierda, targetX, targetY,
                    x - offset, y + 50, offset / 2);
        }
        if (nodo.derecha != null) {
            buscarNodoEnPosicion(nodo.derecha, targetX, targetY,
                    x + offset, y + 50, offset / 2);
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