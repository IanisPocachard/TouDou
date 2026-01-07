package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

import java.util.Optional;

public class ControlSuppTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TreeView<Tache> treeView;

    public ControlSuppTache(Modele modele, TreeView<Tache> treeView) {
        this.modele = modele;
        this.treeView = treeView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TreeItem<Tache> itemSelectionne = treeView.getSelectionModel().getSelectedItem();

        if (itemSelectionne == null) {
            afficherAlerte("Aucune sélection", "Veuillez sélectionner une tâche à supprimer.");
            return;
        }

        Tache tacheASupprimer = itemSelectionne.getValue();
        TreeItem<Tache> parentItem = itemSelectionne.getParent();

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Suppression");
        confirmation.setHeaderText("Supprimer '" + tacheASupprimer.getNom() + "' ?");
        confirmation.setContentText("Cette action est irréversible.");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            if (parentItem == null || parentItem.getValue() == null) {
                // tache primaire
                modele.supprimerTache(tacheASupprimer);

            } else {
                // sous-tache
                Tache parentTache = parentItem.getValue();

                if (parentTache instanceof TachePrimaire) {
                    TachePrimaire parent = (TachePrimaire) parentTache;

                    // on retire la sous-tâche de la liste des dépendances du parent
                    parent.getDependances().remove(tacheASupprimer);

                    // on force le rafraichissement car on a modifié un objet interne
                    modele.notifierObservateurs();
                }
            }
        }
        modele.logger("La tâche '" + tacheASupprimer.getNom() + "' a été supprimée.");
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}