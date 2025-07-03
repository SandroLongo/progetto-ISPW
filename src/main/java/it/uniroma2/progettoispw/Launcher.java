package it.uniroma2.progettoispw;

import it.uniroma2.progettoispw.controller.graphicController.guiGraphicController.GuiApp;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Launcher {
    private static final int PORT = 34567;

    public static void main(String[] args) {
        if (tryStartSocketServer()) {
            // Sono la prima istanza: lancio JavaFX
            Application.launch(App.class, args);
        } else {
            // Già esiste: devo mandargli i miei args e uscire
            sendArgsToRunningInstance(args);
            System.exit(0);
        }
    }

    private static boolean tryStartSocketServer() {
        try {
            ServerSocket server = new ServerSocket(PORT, 1, InetAddress.getLoopbackAddress());
            // lancio in background il thread che ascolta richieste
            new Thread(() -> listenForClients(server), "IPC-Listener").start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void listenForClients(ServerSocket server) {
        while (true) {
            try (Socket client = server.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
                String[] remoteArgs = (String[]) in.readObject();
                Platform.runLater(() -> {
                    // qui dentro, nella JVM principale, apri una nuova finestra
                    App.openNewWindow(remoteArgs);
                });
            } catch (Exception ignored) { }
        }
    }

    private static void sendArgsToRunningInstance(String[] args) {
        try (Socket sock = new Socket(InetAddress.getLoopbackAddress(), PORT);
             ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream())) {
            out.writeObject(args);
        } catch (IOException ignored) { }
    }
}
