package vue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modele.SousTache;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VueListe extends Application {

    private TreeView<Tache> treeView;
    private TreeItem<Tache> root;

    private final ObservableList<TachePrimaire> tachesPrimaires = FXCollections.observableArrayList();
    private final Map<TachePrimaire, TreeItem<Tache>> itemByPrimaire = new HashMap<>();

    private ComboBox<TachePrimaire> cbParent;

    @Override
    public void start(Stage stage) {

        // --- arbre vide ---
        root = new TreeItem<>(null);
        root.setExpanded(true);

        treeView = new TreeView<>(root);
        treeView.setShowRoot(false);

        // --- ajout tâche primaire (simple) ---
        TextField nomP = new TextField();
        nomP.setPromptText("Nom tâche");

        TextField descP = new TextField();
        descP.setPromptText("Description");

        TextField dureeP = new TextField();
        dureeP.setPromptText("Durée");

        DatePicker debut = new DatePicker(LocalDate.now());
        DatePicker echeance = new DatePicker(LocalDate.now().plusDays(1));

        Button addTache = new Button("Ajouter tâche");
        addTache.setOnAction(e -> {
            String n = nomP.getText().trim();
            String d = descP.getText().trim();
            Integer du = parseIntOrNull(dureeP.getText().trim());

            if (n.isEmpty() || du == null) {
                System.out.println("Nom + durée obligatoires");
                return;
            }
            if (d.isEmpty()) d = "(sans description)";

            try {
                TachePrimaire tp = new TachePrimaire(
                        n, d, du,
                        toDate(debut.getValue()),
                        toDate(echeance.getValue())
                );

                // liste parents + arbre
                tachesPrimaires.add(tp);

                TreeItem<Tache> item = new TreeItem<>(tp);
                item.setExpanded(true);
                root.getChildren().add(item);

                itemByPrimaire.put(tp, item);
                cbParent.getSelectionModel().select(tp);

                // reset
                nomP.clear(); descP.clear(); dureeP.clear();

            } catch (Exception ex) {
                System.out.println("Erreur: " + ex.getMessage());
            }
        });

        HBox barTache = new HBox(8, new Label("Tâche:"), nomP, descP, dureeP, debut, echeance, addTache);
        barTache.setPadding(new Insets(10));

        // --- ajout sous-tâche (choix parent obligatoire) ---
        cbParent = new ComboBox<>(tachesPrimaires);
        cbParent.setPromptText("Parent");

        TextField nomS = new TextField();
        nomS.setPromptText("Nom sous-tâche");

        TextField descS = new TextField();
        descS.setPromptText("Description");

        TextField dureeS = new TextField();
        dureeS.setPromptText("Durée");

        Button addSousTache = new Button("Ajouter sous-tâche");
        addSousTache.setOnAction(e -> {
            TachePrimaire parent = cbParent.getValue();
            if (parent == null) {
                System.out.println("Choisis un parent");
                return;
            }

            String n = nomS.getText().trim();
            String d = descS.getText().trim();
            Integer du = parseIntOrNull(dureeS.getText().trim());

            if (n.isEmpty() || du == null) {
                System.out.println("Nom + durée obligatoires");
                return;
            }
            if (d.isEmpty()) d = "(sans description)";

            try {
                SousTache st = new SousTache(n, d, du);

                // lien modèle
                parent.ajoutDependance(st);

                // ajout vue (rapide, pas de recherche récursive)
                TreeItem<Tache> parentItem = itemByPrimaire.get(parent);
                parentItem.getChildren().add(new TreeItem<>(st));
                parentItem.setExpanded(true);

                // reset
                nomS.clear(); descS.clear(); dureeS.clear();

            } catch (Exception ex) {
                System.out.println("Erreur: " + ex.getMessage());
            }
        });

        HBox barSous = new HBox(8, new Label("Sous-tâche:"), cbParent, nomS, descS, dureeS, addSousTache);
        barSous.setPadding(new Insets(10));

        BorderPane pane = new BorderPane();
        pane.setTop(barTache);
        pane.setCenter(treeView);
        pane.setBottom(barSous);

        stage.setScene(new Scene(pane, 1100, 560));
        stage.setTitle("Vue Liste");
        stage.show();
    }

    private static Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return null; }
    }

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) {
        launch();
    }
}
