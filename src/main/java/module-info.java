module it.uniroma2.nutrition {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens it.uniroma2.nutrition to javafx.fxml;
    exports it.uniroma2.nutrition;
    exports it.uniroma2.nutrition.controller.graphic_controller;
    opens it.uniroma2.nutrition.controller.graphic_controller to javafx.fxml;
}