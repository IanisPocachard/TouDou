package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Tache;

import java.util.List;

public class VueArchives extends Stage {

    public VueArchives(List<Tache> lesArchives) {
        this.setTitle("Archives");
        this.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label titre = new Label("Historique des tâches archivées");
        titre.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        ListView<String> listeVisuelle = new ListView<>();

        if (lesArchives.isEmpty()) {
            listeVisuelle.getItems().add("Aucune tâche archivée.");
        } else {
            for (Tache t : lesArchives) {
                listeVisuelle.getItems().add(t.getNom() + " (" + t.getDuree() + " jours)");
            }
        }

        layout.getChildren().addAll(titre, listeVisuelle);

        Scene scene = new Scene(layout);
        this.setScene(scene);
        this.setMaximized(true);
    }
}