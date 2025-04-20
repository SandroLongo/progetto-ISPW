module it.uniroma2.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens it.uniroma2.progettoispw to javafx.fxml;
    exports it.uniroma2.progettoispw;
}