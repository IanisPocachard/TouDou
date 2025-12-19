package vue;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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

    public VueListe(Sujet sujet) {
        this.sujet = sujet;
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

    }

    private void buildVL() {
        setSpacing(10);
        setPadding(new Insets(10));

        root.setExpanded(true);
        treeView.setShowRoot(false);
        treeView.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(Tache item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(item.toString());

                    // Fond clair par défaut
                    String styleNormal = """
                        -fx-background-color: #f3f4f6;
                        -fx-text-fill: #374151;
                        -fx-border-color: #e5e7eb;
                        -fx-padding: 6;
                    """;

                    // Fond spécial si sélectionnée
                    String styleSelected = """
                        -fx-background-color: #3b82f6;
                        -fx-text-fill: white;
                        -fx-border-color: #2563eb;
                        -fx-padding: 6;
                    """;

                    setStyle(isSelected() ? styleSelected : styleNormal);

                    // Écouter les changements de sélection pour mettre à jour le style
                    selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                        setStyle(isNowSelected ? styleSelected : styleNormal);
                    });
                }
            }
        });

        HBox bar = new HBox(10, btnAjouterTache, btnAjouterSousTache, btnSupprimer);

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
                                if (dep instanceof SousTache) item.getChildren().add(new TreeItem<>(dep));
                            }
                        }
                        root.getChildren().add(item);
                    }
                }
            });
        }
    }
}