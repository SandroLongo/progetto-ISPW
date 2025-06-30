package it.uniroma2.progettoispw.controller.graphicController.cliGraphicController;

import javafx.stage.Stage;

import java.io.*;

public class CliApp {

    public static void start() throws RuntimeException, IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe");
        Process process = builder.start();

        // Stream per scrivere comandi
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Invia un comando
        writer.write("echo Shell avviata da Java");
        writer.newLine();
        writer.flush();

        writer.write("dir");
        writer.newLine();
        writer.flush();

        writer.write("exit"); // Chiude la shell
        writer.newLine();
        writer.flush();

        // Stampa le risposte della shell
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}

