package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VueGantt {

    private static final int LARGEUR = 30;
    private static final int HAUTEUR = 18;

    public static void afficher(List<Tache> selection) {

        List<TachePrimaire> taches = new ArrayList<>();
        for (Tache t : selection) {
            if (t instanceof TachePrimaire tp
                    && tp.getDateDebut() != null
                    && tp.getDateEcheance() != null) {
                taches.add(tp);
            }
        }

        if (taches.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Aucune tâche primaire avec dates, ajoute des dates pour afficher le Gantt.").showAndWait();
            return;
        }

        LocalDate min = taches.get(0).getDateDebut();
        LocalDate max = taches.get(0).getDateEcheance();
        for (TachePrimaire tp : taches) {
            if (tp.getDateDebut().isBefore(min)){
                min = tp.getDateDebut();
            }
            if (tp.getDateEcheance().isAfter(max)){
                max = tp.getDateEcheance();
            }
        }

        int jours = (int) ChronoUnit.DAYS.between(min, max) + 1;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(2);
        grid.setVgap(6);

        grid.add(new Label("Tâches"), 0, 0);

        for (int i = 0; i < jours; i++) {
            Label d = new Label(min.plusDays(i).getDayOfMonth() + "");
            d.setStyle("-fx-font-size: 10px; -fx-opacity: 0.7;");
            grid.add(d, i + 1, 0);
        }

        int row = 1;
        for (TachePrimaire tp : taches) {

            Label nom = new Label(tp.getNom());
            nom.setStyle("-fx-font-weight: bold;");
            grid.add(nom, 0, row);

            Pane ligne = new Pane();
            ligne.setPrefHeight(HAUTEUR + 6);
            ligne.setMinWidth(jours * LARGEUR);

            int debutEcheance = (int) ChronoUnit.DAYS.between(min, tp.getDateDebut());
            int largeurEcheance = (int) ChronoUnit.DAYS.between(tp.getDateDebut(), tp.getDateEcheance()) + 1;

            Pane zone = new Pane();
            zone.setLayoutX(debutEcheance * LARGEUR);
            zone.setPrefWidth(largeurEcheance * LARGEUR);
            zone.setPrefHeight(HAUTEUR);
            zone.setStyle("-fx-background-color: rgba(79,70,229,0.25); -fx-background-radius: 6;");

            int largeurReel = tp.getDuree() * LARGEUR;

            Pane bar = new Pane();
            bar.setPrefWidth(largeurReel);
            bar.setPrefHeight(HAUTEUR);
            bar.setLayoutX(debutEcheance * LARGEUR);
            bar.setStyle("-fx-background-color: #4f46e5; -fx-background-radius: 6;");
            bar.setCursor(javafx.scene.Cursor.HAND);

            final double[] ecart = new double[1];

            bar.addEventHandler(MouseEvent.MOUSE_PRESSED, e ->
                    ecart[0] = e.getSceneX() - bar.getLayoutX()
            );

            bar.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
                double newX = e.getSceneX() - ecart[0];

                double minX = debutEcheance * LARGEUR;
                double maxX = minX + zone.getPrefWidth() - bar.getPrefWidth();

                if (newX < minX){
                    newX = minX;
                }
                if (newX > maxX){
                    newX = maxX;
                }

                bar.setLayoutX(newX);
            });

            ligne.getChildren().addAll(zone, bar);
            grid.add(ligne, 1, row, jours, 1);

            row++;
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        Stage stage = new Stage();
        stage.setTitle("Diagramme de Gantt");
        stage.setScene(new Scene(scroll, 900, 600));
        stage.show();
    }
}