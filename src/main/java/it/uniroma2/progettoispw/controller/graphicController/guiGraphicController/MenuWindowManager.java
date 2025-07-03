package it.uniroma2.progettoispw.controller.graphicController.guiGraphicController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MenuWindowManager {
    private Stage mainStage;
    private BorderPane menu;
    private Map<String, Stack<Scene>> SceneMap = new HashMap<String, Stack<Scene>>();

    public MenuWindowManager(Stage mainStage) {
        this.mainStage = mainStage;
    }


    public void createNewStack(String string){

        SceneMap.put(string, new Stack<>());
    }

    public void addScene(String gruppo, String fxml, Object... args) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(loader.load());
        SceneMap.get(gruppo).push(scene);
        ((GuiGraphicController)loader.getController()).initialize(args);
        System.out.println("aggiunto"+ SceneMap.get(gruppo).peek().getRoot() + "con" + fxml);
    }

    public void addSceneAndShow(String gruppo, String fxml, Object...  args) throws IOException {
        addScene(gruppo, fxml, args);
        show(gruppo);
    }

    public void show(String gruppo){
        menu.setCenter(SceneMap.get(gruppo).peek().getRoot());
        System.out.println("show"+ SceneMap.get(gruppo).peek().getRoot());
    }
    public void deleteAndcomeBack(String gruppo){
        SceneMap.get(gruppo).pop();
        menu.setCenter(SceneMap.get(gruppo).peek().getRoot());
    }

    public void deleteStack(String gruppo){
        SceneMap.remove(gruppo);
    }

    public void deleteTop(String gruppo){
        Scene scene = SceneMap.get(gruppo).pop();
        System.out.println("tolto"+ scene.getRoot());
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setMenu(BorderPane menu) {
        this.menu = menu;
        mainStage.setScene(new Scene(menu));
        mainStage.show();
    }


    public void loadRicercaMedicinali() {
    }
}

