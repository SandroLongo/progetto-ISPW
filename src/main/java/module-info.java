module it.uniroma2.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens it.uniroma2.progettoispw to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphicController to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphicController.guiGraphicController to javafx.fxml;
    exports it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;
    exports it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;
    exports it.uniroma2.progettoispw;
    exports it.uniroma2.progettoispw.controller.bean;
    exports it.uniroma2.progettoispw.model.domain;
    exports it.uniroma2.progettoispw.controller.graphicController;
    exports it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic;
    opens it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.pazientegraphic to javafx.fxml;
    exports it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.medicographic;
    opens it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.medicographic to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphicController.cliGraphicController to javafx.fxml;
}