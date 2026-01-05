package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;

public class ControlAddDependance implements EventHandler<ActionEvent> {

    private Modele modele;
    private Tache tache1;
    private ComboBox<Tache> cbTaches;
    private Stage fenetre;

    public ControlAddDependance(Modele modele, Tache tache1, ComboBox<Tache> cbTaches, Stage fenetre) {
        this.modele = modele;
        this.tache1 = tache1;
        this.cbTaches = cbTaches;
        this.fenetre = fenetre;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }

}
