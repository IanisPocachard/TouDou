package modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerFichier {

    private static final String FICHIER_LOG = "resources/logs.txt";

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FICHIER_LOG, true))) { // true = append

            writer.write(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("[yyyy-MM-dd HH:mm:ss]")) + " - " + message
            );
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Erreur écriture log : " + e.getMessage());
        }
    }
}

hide ControlAddArchive
hide ControlAddDependances
hide ControlAddSousTache
hide ControlAddTache
hide ControlBtnArchives
hide ControlBtnKanbas
hide ControlBtnListeTaches
hide ControlSuppTache
hide ControlValideSousTache
hide DragDropController
hide VueListe
hide VueBureau
hide VBox
hide Hbox
hide Observateur
hide TextArea
hide VueHistorique
hide Stage
hide VueAjoutDependance
hide VueAjoutSousTache
hide VueAjoutTache
hide VueArchives
hide LoggerFichier
hide TacheSerializer
hide Manager
hide TacheManager
hide FabriqueSousTache
hide FabriqueTache
hide FabriqueTachePrimaire
hide VueGantt
hide Serializable
hide Modele
hide Sujet
hide VueFormGantt