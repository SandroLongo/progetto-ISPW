package it.uniroma2.progettoispw;

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

    private static volatile ServerSocket serverSocket; // aggiunto
    private static volatile boolean running = true;    // aggiunto

    public static void main(String[] args) {
        if (tryStartSocketServer()) {
            Application.launch(App.class, args);
        } else {
            sendArgsToRunningInstance(args);
            System.exit(0);
        }
    }

    public static void shutdownSocketServer() {  // chiamabile da WindowCounter
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // forza chiusura del listener
            }
        } catch (IOException ignored) {
            //ignoro
        }
    }

    private static boolean tryStartSocketServer() {
        try {
            serverSocket = new ServerSocket(PORT, 1, InetAddress.getLoopbackAddress());
            new Thread(() -> listenForClients(serverSocket), "IPC-Listener").start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void listenForClients(ServerSocket server) {
        while (running && !server.isClosed()) {
            try (Socket client = server.accept();
                 ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
                Platform.runLater(App::openNewWindow);
            } catch (IOException e) {
                if (!running || server.isClosed()) break; // interrompe il loop
            } catch (Exception ignored) {
                //ignoro
            }
        }
    }

    private static void sendArgsToRunningInstance(String[] args) {
        try (Socket sock = new Socket(InetAddress.getLoopbackAddress(), PORT);
             ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream())) {
            out.writeObject(args);
        } catch (IOException ignored) {
            //ignoro
        }
    }
}
