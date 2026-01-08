package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;
import vue.VueAjoutSousTache;
import vue.VueListe;

public class ControlFormAddSousTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private VueListe vueListe;

    public ControlFormAddSousTache(Modele modele, VueListe vueListe) {
        this.modele = modele;
        this.vueListe = vueListe;

    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Tache tache = vueListe.getTacheSelectionnee();
        System.out.println(tache);
        if (tache instanceof TachePrimaire) {
            VueAjoutSousTache vue = new VueAjoutSousTache(modele, (TachePrimaire) tache);
            vue.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Sélection invalide");
            alert.setContentText("Veuillez sélectionner une Tâche Primaire pour lui ajouter une sous-tâche.");
            alert.showAndWait();
        }
    }

}
