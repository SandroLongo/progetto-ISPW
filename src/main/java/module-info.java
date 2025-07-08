module it.uniroma2.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens it.uniroma2.progettoispw to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphic.controller to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller to javafx.fxml;
    exports it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller;
    exports it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;
    exports it.uniroma2.progettoispw;
    exports it.uniroma2.progettoispw.controller.bean;
    exports it.uniroma2.progettoispw.model.domain;
    exports it.uniroma2.progettoispw.model.dao;
    exports it.uniroma2.progettoispw.model.dao.dbfiledao;
    exports it.uniroma2.progettoispw.controller.graphic.controller;
    exports it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic;
    opens it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.patientgraphic to javafx.fxml;
    exports it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.doctorgraphic;
    opens it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller.doctorgraphic to javafx.fxml;
    opens it.uniroma2.progettoispw.controller.graphic.controller.cli.graphic.controller to javafx.fxml;
}