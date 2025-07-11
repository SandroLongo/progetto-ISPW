package it.uniroma2.progettoispw.controller.graphic.controller.gui.graphic.controller;

import it.uniroma2.progettoispw.WindowCounter;
import it.uniroma2.progettoispw.controller.bean.AuthenticationBean;
import it.uniroma2.progettoispw.controller.controller.applicativi.LogInController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class WindowManager {
    private Stage mainStage;
    private BorderPane menu;
    private Map<String, Stack<Scene>> sceneMap = new HashMap<String, Stack<Scene>>();
    private Scene loginScene;
    private Scene registerScene;

    public WindowManager(Stage mainStage) {
        this.mainStage = mainStage;
    }


    public void createNewStack(String string){

        sceneMap.put(string, new Stack<>());
    }

    public void addScene(String gruppo, String fxml, Object... args) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(loader.load());
        sceneMap.get(gruppo).push(scene);
        ((GuiGraphicController)loader.getController()).initialize(args);
    }

    public void addSceneAndShow(String gruppo, String fxml, Object...  args) throws IOException {
        addScene(gruppo, fxml, args);
        show(gruppo);
    }

    public void show(String gruppo){
        menu.setCenter(sceneMap.get(gruppo).peek().getRoot());
    }
    public void deleteAndcomeBack(String gruppo){
        sceneMap.get(gruppo).pop();
        menu.setCenter(sceneMap.get(gruppo).peek().getRoot());
    }

    public void deleteStack(String gruppo){
        sceneMap.remove(gruppo);
    }

    public void deleteTop(String gruppo){
        sceneMap.get(gruppo).pop();
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setLoginScene(String fxml, Object... args) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(loader.load());
        this.loginScene = scene;
        ((GuiGraphicController)loader.getController()).initialize(args);
    }

    public void setRegisterScene(String fxml, Object... args) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(loader.load());
        this.registerScene = scene;
        ((GuiGraphicController)loader.getController()).initialize(args);
    }

    public void showRegisterScene(){
        mainStage.setScene(registerScene);
        mainStage.show();
    }

    public void showLogin(){
        mainStage.setScene(loginScene);
        mainStage.show();
    }
    public void setMenu(BorderPane menu) {
        this.menu = menu;
        mainStage.setScene(new Scene(menu));
        mainStage.show();
    }

    public void resetStacks() {
        sceneMap.clear();
        this.menu = null;
    }

    public void resetExit(){
        mainStage.setOnCloseRequest(event -> {
            WindowCounter.getInstance().decrement();
        });
    }


    public void setExit(AuthenticationBean authenticationBean) {
        mainStage.setOnCloseRequest(event -> {
            LogInController logOutController = new LogInController();
            logOutController.logOut(authenticationBean.getCodice());
            WindowCounter.getInstance().decrement();
        });
    }
}

