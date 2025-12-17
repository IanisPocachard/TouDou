package modele;

import vue.Observateur;

import java.util.ArrayList;

public class Modele implements Sujet {

    private ArrayList<Observateur> observateurs;

    private ArrayList<Tache> taches;

    public Modele() {
        this.observateurs = new ArrayList<>();
        this.taches = new ArrayList<>();
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    @Override
    public void retirerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur o : observateurs) {
            o.actualiser(this);
        }
    }

}
