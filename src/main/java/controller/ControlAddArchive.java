package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

public class ControlAddArchive implements EventHandler<ActionEvent> {

    private Modele modele;
    private TreeView<Tache> treeView;

    public ControlAddArchive(Modele modele, TreeView<Tache> treeView) {
        this.modele = modele;
        this.treeView = treeView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TreeItem<Tache> item = treeView.getSelectionModel().getSelectedItem();

        if (item == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner une tâche à archiver.");
            return;
        }

        Tache tache = item.getValue();

        if (tache instanceof TachePrimaire) {
            TachePrimaire tp = (TachePrimaire) tache;

            if (tp.isArchivee()) {
                afficherAlerte("Attention", "Cette tâche est déjà archivée.");
                return;
            }

            modele.archiverTache(tp);

        } else {
            afficherAlerte("Impossible", "Vous ne pouvez archiver qu'une tâche principale.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}