package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphicController.cliGraphicController.CliApp;
public class ISPWApplication {

    public static void main(String[] args) {
        javafx.application.Application.launch(CliApp.class, args);
        //javafx.application.Application.launch(GuiApp.class, args);
    }
}