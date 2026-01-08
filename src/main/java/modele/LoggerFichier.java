package modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoggerFichier {

    private static final String FICHIER_LOG = "resources/logs.txt";

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FICHIER_LOG, true))) { // true = append

            writer.write(
                    LocalDateTime.now() + " - " + message
            );
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Erreur écriture log : " + e.getMessage());
        }
    }
}