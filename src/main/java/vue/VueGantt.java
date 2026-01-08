package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VueGantt {

    private static final int LARGEUR_JOUR = 30;
    private static final int HAUTEUR = 18;

    public static void afficher(List<Tache> selection) {

        List<TachePrimaire> taches = new ArrayList<>();
        for (Tache t : selection) {
            if (t instanceof TachePrimaire tp &&
                    tp.getDateDebut() != null &&
                    tp.getDateEcheance() != null) {
                taches.add(tp);
            }
        }

        if (taches.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Aucune tâche primaire avec dates").showAndWait();
            return;
        }

        // bornes des dates
        LocalDate min = taches.get(0).getDateDebut();
        LocalDate max = taches.get(0).getDateEcheance();
        for (TachePrimaire tp : taches) {
            if (tp.getDateDebut().isBefore(min)) min = tp.getDateDebut();
            if (tp.getDateEcheance().isAfter(max)) max = tp.getDateEcheance();
        }

        int jours = (int) ChronoUnit.DAYS.between(min, max) + 1;

        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        // colonne noms
        ColumnConstraints colNom = new ColumnConstraints();
        colNom.setPrefWidth(120);
        grid.getColumnConstraints().add(colNom);

        // colonnes jours
        for (int i = 0; i < jours; i++) {
            ColumnConstraints colJour = new ColumnConstraints();
            colJour.setPrefWidth(LARGEUR_JOUR);
            colJour.setMinWidth(LARGEUR_JOUR);
            colJour.setMaxWidth(LARGEUR_JOUR);
            grid.getColumnConstraints().add(colJour);
        }

        // entête des dates
        grid.add(new Label("Tâches"), 0, 0);
        for (int i = 0; i < jours; i++) {
            LocalDate date = min.plusDays(i);
            Label lab = new Label(date.getDayOfMonth() + "/" + date.getMonthValue());
            lab.setMinWidth(LARGEUR_JOUR);
            lab.setStyle("-fx-font-size: 10px; -fx-alignment: center;");
            grid.add(lab, i + 1, 0);
        }

        // lignes avec barres
        int ligne = 1;
        for (TachePrimaire tp : taches) {

            grid.add(new Label(tp.getNom()), 0, ligne);

            int decalageDebut = (int) ChronoUnit.DAYS.between(min, tp.getDateDebut());
            int decalageEcheance = (int) ChronoUnit.DAYS.between(min, tp.getDateEcheance());
            int duree = tp.getDuree();
            double largeurZone = (decalageEcheance - decalageDebut + 1) * LARGEUR_JOUR;

            Pane zone = new Pane();
            zone.setPrefHeight(HAUTEUR + 6);
            zone.setPrefWidth(largeurZone);

            Pane fond = new Pane();
            fond.setPrefSize(largeurZone, HAUTEUR);
            fond.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 6;");

            Pane barre = new Pane();
            barre.setPrefSize(duree * LARGEUR_JOUR, HAUTEUR);

            // couleur selon l'etat
            String couleur;
            switch (tp.getEtat()) {
                case TachePrimaire.A_FAIRE -> couleur = "#FFC108";     // TO DO
                case TachePrimaire.EN_COURS -> couleur = "#FF5A08";   // IN PROGRESS
                case TachePrimaire.A_TESTER -> couleur = "#24739B";   // TESTING
                case TachePrimaire.VALIDEE -> couleur = "#36CBC1"; // DONE
                default -> couleur = "#4f46e5";                       // bleu par défaut
            }
            barre.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 6;");

            // durée visible sur la barre
            Label labelDuree = new Label(duree + "j");
            labelDuree.setStyle("-fx-font-size: 10px; -fx-text-fill: white;");
            labelDuree.setLayoutX(3);
            labelDuree.setLayoutY(0);
            barre.getChildren().add(labelDuree);

            final double[] decalageSouris = new double[1];
            barre.setOnMousePressed(e -> decalageSouris[0] = e.getX());
            barre.setOnMouseDragged(e -> {
                double newX = barre.getLayoutX() + e.getX() - decalageSouris[0];
                double maxX = largeurZone - barre.getWidth();
                newX = Math.round(newX / LARGEUR_JOUR) * LARGEUR_JOUR;
                newX = Math.max(0, Math.min(newX, maxX));
                barre.setLayoutX(newX);
            });

            zone.getChildren().addAll(fond, barre);
            grid.add(zone, decalageDebut + 1, ligne);

            ligne++;
        }

        ScrollPane scroll = new ScrollPane(grid);
        VBox root = new VBox(scroll);

        Stage stage = new Stage();
        stage.setTitle("Diagramme de Gantt");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
}