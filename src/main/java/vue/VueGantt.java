package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VueGantt {

    public static void afficher(List<Tache> selection) {

        // garder uniquement les tâches primaires qui ont des dates
        List<TachePrimaire> taches = new ArrayList<>();
        for (Tache t : selection) {
            if (t instanceof TachePrimaire tp) {
                if (tp.getDateDebut() != null && tp.getDateEcheance() != null) {
                    taches.add(tp);
                }
            }
        }

        if (taches.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Aucune tâche primaire avec dates (date début + date échéance)\n" +
                            "Ajoute des dates aux tâches pour afficher le Gantt !").showAndWait();
            return;
        }

        // min et max sur les dates
        LocalDate min = taches.get(0).getDateDebut();
        LocalDate max = taches.get(0).getDateEcheance();

        for (TachePrimaire tp : taches) {
            LocalDate start = tp.getDateDebut();
            LocalDate end   = tp.getDateEcheance();

            if (start.isBefore(min)) min = start;
            if (end.isAfter(max)) max = end;
        }

        int days = (int) (ChronoUnit.DAYS.between(min, max) + 1);

        // grille
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(6);
        grid.setPadding(new Insets(10));
        grid.getColumnConstraints().add(new ColumnConstraints(120));

        for (int c = 0; c < days; c++) {
            grid.getColumnConstraints().add(new ColumnConstraints(35));
        }


        // header dates
        grid.add(new Label("Tâches"), 0, 0);
        for (int c = 0; c < days; c++) {
            LocalDate d = min.plusDays(c);
            Label lab = new Label(d.getDayOfMonth() + "/" + d.getMonthValue());
            lab.setStyle("-fx-font-size: 10px; -fx-opacity: 0.75;");

            if (days <= 40 || c % 2 == 0) {
                grid.add(lab, c + 1, 0);
            }
        }

        // barres
        int row = 1;
        for (TachePrimaire tp : taches) {
            LocalDate start = tp.getDateDebut();
            LocalDate end = tp.getDateEcheance();

            // sécurité si end < start
            if (end.isBefore(start)) {
                end = start;
            }

            int decalage = (int) ChronoUnit.DAYS.between(min, start);
            int length = tp.getDuree();

            Label name = new Label(tp.getNom());
            name.setStyle("-fx-font-weight: bold;");
            grid.add(name, 0, row);

            Label bar = new Label(" ");
            bar.setMaxWidth(Double.MAX_VALUE);
            bar.setPrefHeight(18);
            bar.setStyle("-fx-background-color: #4f46e5; -fx-background-radius: 6;");
            bar.setPadding(new Insets(0, 4, 0, 4));
            grid.add(bar, decalage + 1, row, length, 1);

            row++;
        }

        // fenêtre
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        VBox root = new VBox(scroll);
        root.setPadding(new Insets(10));
        VBox.setVgrow(scroll, javafx.scene.layout.Priority.ALWAYS);

        Stage stage = new Stage();
        stage.setTitle("Diagramme de Gantt");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }
}