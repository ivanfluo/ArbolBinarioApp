module com.example.arbolbinario_patronesdisenio {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.arbolbinario_patronesdisenio to javafx.fxml;
    exports com.example.arbolbinario_patronesdisenio;
}