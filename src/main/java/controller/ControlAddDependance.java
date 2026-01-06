package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

public class ControlAddDependance implements EventHandler<ActionEvent> {

    private Modele modele;
    private TachePrimaire tache1;
    private ComboBox<Tache> cbTaches;
    private Stage fenetre;

    public ControlAddDependance(Modele modele, TachePrimaire tache1, ComboBox<Tache> cbTaches, Stage fenetre) {
        this.modele = modele;
        this.tache1 = tache1;
        this.cbTaches = cbTaches;
        this.fenetre = fenetre;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Tache tache2 = cbTaches.getValue();

            if (tache2 == null) throw new Exception("Veuillez sélectionner une tâche");

            tache1.ajoutDependance(tache2);

            modele.notifierObservateurs();

            fenetre.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
