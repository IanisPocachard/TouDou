package vue;

import controller.ControlValiderSousTache;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import modele.Modele;
import modele.SousTache;
import modele.Sujet;
import modele.Tache;
import modele.TachePrimaire;
import java.util.ArrayList;
import java.util.List;

public class VueListe extends VBox implements Observateur {

    private final Sujet sujet;
    private final TreeItem<Tache> root = new TreeItem<>(null);
    private final TreeView<Tache> treeView = new TreeView<>(root);

    private final Button btnAjouterTache = new Button("Ajouter une tâche");
    private final Button btnAjouterSousTache = new Button("Ajouter une sous-tâche");
    private final Button btnSupprimer = new Button("Supprimer");
    private final Button btnAjouterDependance = new Button("Ajouter une dépendance");
    private final ControlValiderSousTache controlValideSousTache;

    public VueListe(Sujet sujet) {
        this.sujet = sujet;
        this.controlValideSousTache = new ControlValiderSousTache((Modele)sujet);
        buildVL();
        if (this.sujet != null) this.sujet.ajouterObservateur(this);
        actualiser(this.sujet);

        // STYLE DES BOUTONS
        String styleSousBouton = """
            -fx-background-color: transparent;
            -fx-text-fill: #3b82f6;
            -fx-font-size: 13px;
            -fx-padding: 6 12;
            -fx-border-color: #3b82f6;
            -fx-border-radius: 6;
            -fx-background-radius: 6;
        """;
        btnAjouterTache.setStyle(styleSousBouton);
        btnAjouterSousTache.setStyle(styleSousBouton);
        btnSupprimer.setStyle(styleSousBouton);
        btnAjouterDependance.setStyle(styleSousBouton);

    }

    private void buildVL() {
        setSpacing(10);
        setPadding(new Insets(10));

        root.setExpanded(true);
        treeView.setShowRoot(false);
        treeView.setCellFactory(tv -> new CheckBoxTreeCell<>() {

            @Override
            public void updateItem(Tache item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                    return;
                }
                setText(item.toString());
                setGraphicTextGap(20);

                String styleNormal = """
            -fx-background-color: #f3f4f6;
            -fx-text-fill: #374151;
            -fx-border-color: #e5e7eb;
            -fx-padding: 6;
        """;
                String styleSelected = """
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-border-color: #2563eb;
            -fx-padding: 6;
        """;

                setStyle(isSelected() ? styleSelected : styleNormal);

                selectedProperty().addListener((obs, oldVal, newVal) ->
                        setStyle(newVal ? styleSelected : styleNormal)
                );

                // 🔹 PAS de checkbox pour les tâches principales
                if (!(getTreeItem() instanceof CheckBoxTreeItem)) {
                    setGraphic(null);
                }
            }
        });

        HBox boutons1 = new HBox(10, btnAjouterTache, btnAjouterSousTache);
        HBox boutons2 = new HBox(10, btnSupprimer, btnAjouterDependance);
        VBox bar = new VBox(10, boutons1, boutons2);


        btnAjouterTache.setOnAction(e -> {
            if (sujet instanceof Modele) {
                VueAjoutTache fenetreAjout = new VueAjoutTache((Modele) sujet);
                fenetreAjout.showAndWait();
            }
        });

        btnAjouterSousTache.setOnAction(e -> {
            TreeItem<Tache> item = treeView.getSelectionModel().getSelectedItem();

            if (item != null && item.getValue() instanceof TachePrimaire) {
                TachePrimaire parent = (TachePrimaire) item.getValue();

                if (sujet instanceof Modele) {
                    new VueAjoutSousTache((Modele) sujet, parent).showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Sélection invalide");
                alert.setContentText("Veuillez sélectionner une Tâche Primaire pour lui ajouter une sous-tâche.");
                alert.showAndWait();
            }
        });

        btnAjouterDependance.setOnAction(e -> {
            TreeItem<Tache> item = treeView.getSelectionModel().getSelectedItem();

            if (item != null && item.getValue() instanceof TachePrimaire) {
                TachePrimaire tache1 = (TachePrimaire) item.getValue();

                if (sujet instanceof Modele) {
                    new VueAjoutDependance((Modele) sujet, tache1).showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Sélection invalide");
                alert.setContentText("Veuillez sélectionner une Tâche Primaire pour lui ajouter une dépendance.");
                alert.showAndWait();
            }
        });

        VBox.setVgrow(treeView, Priority.ALWAYS);
        getChildren().addAll(bar, treeView);
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Modele m) {
            List<Tache> racines = m.getTaches();
            Platform.runLater(() -> {
                root.getChildren().clear();
                if (racines != null) {
                    for (Tache t : racines) {
                        TreeItem<Tache> item = new TreeItem<>(t);
                        item.setExpanded(false);
                        if (t instanceof TachePrimaire tp && tp.getDependances() != null) {
                            for (Tache dep : tp.getDependances()) {
                                if (dep instanceof SousTache sousTache) {

                                    CheckBoxTreeItem<Tache> sousItem =
                                            new CheckBoxTreeItem<>(sousTache);

                                    // État initial synchronisé avec le modèle
                                    sousItem.setSelected(sousTache.getValide());

                                    // Connexion checkbox → contrôleur
                                    sousItem.selectedProperty().addListener((obs, oldVal, newVal) -> {
                                        this.controlValideSousTache.executer(sousTache);
                                    });

                                    item.getChildren().add(sousItem);
                                }
                            }
                        }
                        root.getChildren().add(item);
                    }
                }
            });
        }
    }
}