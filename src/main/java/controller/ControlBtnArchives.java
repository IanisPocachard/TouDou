package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import modele.Modele;
import modele.Tache;
import vue.VueArchives;

import java.util.List;

public class ControlBtnArchives implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControlBtnArchives(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        List<Tache> archives = modele.getTachesArchivees();

        VueArchives fenetre = new VueArchives(archives);
        fenetre.show();
    }
}