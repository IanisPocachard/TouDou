package vue;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    private final Button btnAjouterTache = new Button("Ajouter tâche");
    private final Button btnAjouterSousTache = new Button("Ajouter sous-tâche");
    private final Button btnSupprimer = new Button("Supprimer");

    public VueListe(Sujet sujet) {
        this.sujet = sujet;

        buildVL();

        if (this.sujet != null) {
            this.sujet.ajouterObservateur(this);
        }

        actualiser(this.sujet);
    }

    private void buildVL() {
        setSpacing(10);
        setPadding(new Insets(10));

        root.setExpanded(true);
        treeView.setShowRoot(false);

        treeView.setCellFactory(new Callback<>() {
            @Override
            public TreeCell<Tache> call(TreeView<Tache> tv) {
                TreeCell<Tache> cell = new TreeCell<>() {
                    @Override
                    protected void updateItem(Tache tache, boolean empty) {
                        super.updateItem(tache, empty);
                        if (empty || tache == null) {
                            setText("");
                            setStyle("");
                        } else {
                            setText(tache.toString());
                            setStyle(" -fx-background-color: grey ; ");
                        }
                    }
                };

                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (!cell.isEmpty() && cell.getItem() != null) {
                        // click controller
                    }
                });

                return cell;
            }
        });

        HBox bar = new HBox(10, btnAjouterTache, btnAjouterSousTache, btnSupprimer);

        btnAjouterTache.setOnAction(e -> { /* controller */ });
        btnAjouterSousTache.setOnAction(e -> { /* controller */ });
        btnSupprimer.setOnAction(e -> { /* controller */ });

        VBox.setVgrow(treeView, Priority.ALWAYS);
        getChildren().addAll(bar, treeView);
    }

    @Override
    public void actualiser(Sujet s) {
        if (s == null) {
            return;
        }

        if (!(s instanceof Modele m)) {
            return;
        }

        List<Tache> racines = m.readAll();

        Platform.runLater(() -> {
            root.getChildren().clear();
            if (racines == null || racines.isEmpty()) return;

            for (Tache t : racines) {
                TreeItem<Tache> item = new TreeItem<>(t);
                item.setExpanded(true);

                if (t instanceof TachePrimaire tp) {
                    for (Tache st : getSousTaches(tp)) {
                        item.getChildren().add(new TreeItem<>(st));
                    }
                }

                root.getChildren().add(item);
            }
        });
    }

    private List<Tache> getSousTaches(TachePrimaire tp) {
        List<Tache> listeSousTaches = new ArrayList<>();
        for (int i = 0; i < tp.getDependances().size(); i++) {
            if (tp.getDependances().get(i) instanceof SousTache) {
                listeSousTaches.add(tp.getDependances().get(i));
            }
        }
        return listeSousTaches;
    }

    public TreeView<Tache> getTreeView() {
        return treeView;
    }
}