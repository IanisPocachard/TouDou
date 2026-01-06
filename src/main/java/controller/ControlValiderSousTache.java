package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Modele;
import modele.SousTache;
import modele.Sujet;

public class ControlValiderSousTache {

    private Modele modele;

    public ControlValiderSousTache(Modele modele) {
        this.modele = (Modele)modele;
    }

    public void executer(SousTache sousTache, boolean nouvelEtat) {
        sousTache.toggleValide();
        modele.notifierObservateurs();
    }

}
