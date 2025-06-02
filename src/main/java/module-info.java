module it.uniroma2.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens it.uniroma2.progettoispw to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphicController to javafx.fxml;
    exports it.uniroma2.progettoispw;
}