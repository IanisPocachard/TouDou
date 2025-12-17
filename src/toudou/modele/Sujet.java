package modele;

import vue.Observateur;

public interface Sujet {

    public void ajouterObservateur(Observateur o);

    public void retirerObservateur(Observateur o);

    public void notifierObservateurs();

}
