package modele;

import modele.manager.TacheManager;
import vue.Observateur;

import java.util.ArrayList;
import java.util.List;

public class Modele implements Sujet {

    private ArrayList<Observateur> observateurs;
    private List<Tache> taches;
    private TacheManager manager;

    public Modele() {
        this.observateurs = new ArrayList<>();
        this.manager = new TacheManager();
        this.taches = manager.readAll();
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    @Override
    public void retirerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    public void changerEtat(int idTache, int etatCible){
        for (Tache tache : taches) {
            if (tache.getId() == idTache) {
                try {
                    tache.setEtat(etatCible);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void notifierObservateurs() {
        for (Observateur o : observateurs) {
            o.actualiser(this);
        }
    }

    public List<Tache> getTaches() {
        taches = manager.readAll();
        return taches;
    }

    public Tache getTache(int index) {
        return manager.read(index);
    }
}
