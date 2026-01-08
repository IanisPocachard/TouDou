package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;
import vue.VueListe;

public class ControlAddArchive implements EventHandler<ActionEvent> {

    private Modele modele;
    private VueListe vueListe;

    public ControlAddArchive(Modele modele, VueListe vueListe) {
        this.modele = modele;
        this.vueListe = vueListe;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TreeView<Tache> treeView = vueListe.getTreeView();
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
            modele.logger("La tache '" + tp.getNom() + "' a été archivée");

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