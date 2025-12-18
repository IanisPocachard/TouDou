package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;

import java.util.ArrayList;
import java.util.List;

public class VueFormGantt {

    public static void afficher(Modele modele) {
        Stage stage = new Stage();
        stage.setTitle("Sélection - Diagramme de Gantt");

        VBox root = new VBox(10);
        root.setPadding(new Insets(12));

        Label titre = new Label("Choisis les tâches à afficher :");
        titre.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        VBox boxChecks = new VBox(6);

        List<CheckBox> checks = new ArrayList<>();
        for (Tache t : modele.getTaches()) {
            CheckBox cb = new CheckBox(t.toString());
            cb.setUserData(t);
            checks.add(cb);
            boxChecks.getChildren().add(cb);
        }

        ScrollPane scroll = new ScrollPane(boxChecks);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(400);

        Button btnValider = new Button("Valider");
        Button btnAnnuler = new Button("Annuler");

        HBox actions = new HBox(10, btnAnnuler, btnValider);
        actions.setStyle("-fx-alignment: center-right;");

        root.getChildren().addAll(titre, scroll, actions);

        btnAnnuler.setOnAction(e -> stage.close());

        btnValider.setOnAction(e -> {
            List<Tache> selection = new ArrayList<>();
            for (CheckBox cb : checks) {
                if (cb.isSelected()) selection.add((Tache) cb.getUserData());
            }

            if (selection.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Sélectionne au moins une tâche.").showAndWait();
                return;
            }

            VueGantt.afficher(selection);

            stage.close();
        });

        stage.setScene(new Scene(root, 520, 520));
        stage.show();
    }
}