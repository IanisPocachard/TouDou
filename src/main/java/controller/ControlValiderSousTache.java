package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Modele;
import modele.SousTache;
import modele.Sujet;

public class ControlValiderSousTache {

    private Modele modele;

    public ControlValiderSousTache(Modele modele) {
        this.modele = modele;
    }

    public void executer(SousTache sousTache) {
        sousTache.toggleValide();

        if(sousTache.getValide()) {
            this.modele.logger("Sous-tache '" + sousTache.getNom() + "' validée");
        }else {
            this.modele.logger("Sous-tache '" + sousTache.getNom() + "' annuler");
        }
    }

}
