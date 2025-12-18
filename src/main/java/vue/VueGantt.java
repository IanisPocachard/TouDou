package vue;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VueGantt {

    public static void afficher(List<Tache> selection) {
        List<TachePrimaire> taches = new ArrayList<>();
        for (Tache t : selection) {
            if (t instanceof TachePrimaire tp) taches.add(tp);
        }

        if (taches.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Aucune tâche primaire sélectionnée !").showAndWait();
            return;
        }

        LocalDate min = null, max = null;
        for (TachePrimaire tp : taches) {
            LocalDate d1 = toLocalDate(tp.getDateDebut());
            LocalDate d2 = toLocalDate(tp.getDateEcheance());
            if (d1 == null) {
                continue;
            }
            if (d2 == null) {
                d2 = d1;
            }

            if (min == null || d1.isBefore(min)) {
                min = d1;
            }
            if (max == null || d2.isAfter(max)) {
                max = d2;
            }
        }

        if (min == null || max == null) {
            new Alert(Alert.AlertType.WARNING, "Dates manquantes.").showAndWait();
            return;
        }

        int days = (int) (ChronoUnit.DAYS.between(min, max) + 1);

        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(6);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Tâches"), 0, 0);
        for (int c = 0; c < days; c++) {
            LocalDate d = min.plusDays(c);
            Label lab = new Label(d.getDayOfMonth() + "/" + d.getMonthValue());
            lab.setStyle("-fx-font-size: 10px; -fx-opacity: 0.75;");
            lab.setMinWidth(35);
            if (days <= 40 || c % 2 == 0) grid.add(lab, c + 1, 0);
        }

        int row = 1;
        for (TachePrimaire tp : taches) {
            LocalDate start = toLocalDate(tp.getDateDebut());
            LocalDate end   = toLocalDate(tp.getDateEcheance());
            if (start == null) continue;
            if (end == null) end = start;

            int offset = (int) ChronoUnit.DAYS.between(min, start);
            int length = (int) (ChronoUnit.DAYS.between(start, end) + 1);
            if (length < 1) length = 1;

            Label name = new Label(tp.getNom());
            name.setStyle("-fx-font-weight: bold;");
            grid.add(name, 0, row);

            Label bar = new Label(" ");
            bar.setMinHeight(18);
            bar.setStyle("-fx-background-color: #4f46e5; -fx-background-radius: 6;");
            grid.add(bar, offset + 1, row, length, 1);

            row++;
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        VBox root = new VBox(scroll);
        root.setPadding(new Insets(10));

        Stage stage = new Stage();
        stage.setTitle("Diagramme de Gantt");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    private static LocalDate toLocalDate(Date d) {
        if (d == null) return null;
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}