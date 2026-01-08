package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;
import vue.VueAjoutDependance;
import vue.VueListe;

public class ControlFormAddDependance implements EventHandler<ActionEvent> {

    private Modele modele;
    private VueListe vueListe;

    public ControlFormAddDependance(Modele modele, VueListe vueListe) {
        this.modele = modele;
        this.vueListe = vueListe;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        TreeView<Tache> treeView = vueListe.getTreeView();
        TreeItem<Tache> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null && item.getValue() instanceof TachePrimaire) {
            TachePrimaire tache1 = (TachePrimaire) item.getValue();
            new VueAjoutDependance(modele, tache1).showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Sélection invalide");
            alert.setContentText("Veuillez sélectionner une Tâche Primaire pour lui ajouter une dépendance.");
            alert.showAndWait();
        }
    }

}
