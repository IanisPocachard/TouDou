package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Modele;
import vue.VueAjoutTache;

public class ControlFormAddTache implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControlFormAddTache(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        VueAjoutTache vue = new VueAjoutTache(modele);
        vue.show();
    }

}
