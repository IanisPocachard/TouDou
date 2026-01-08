package modele.manager;

import modele.SousTache;
import modele.Tache;
import modele.TachePrimaire;
import modele.TacheSerializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheManager implements Manager {

    private List<Tache> taches;

    public TacheManager() {
        try {
            List<Tache> temp = TacheSerializer.lireFichier();
            if (temp == null) {
                taches = new ArrayList();
            } else {
                taches = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            taches = new ArrayList();
        }
    }

    // ###################
    // ### PERSISTENCE ###
    // ###################
    private void sauvegarder() {
        try {
            TacheSerializer.ecrireFichier(taches);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveChanges() {
        sauvegarder();
    }

    // ##############
    // ### CREATE ###
    // ##############
    @Override
    public void create(Tache tache) {
        taches.add(tache);
        sauvegarder();
    }

    // ############
    // ### READ ###
    // ############
    @Override
    public Tache read(int index) {
        if (index < 0 || index >= taches.size()) {
            return null;
        }
        return taches.get(index);
    }

    @Override
    public List<Tache> readAll() {
        return taches;
    }

    // ##############
    // ### UPDATE ###
    // ##############
    @Override
    public boolean update(int index, String nom, String description, int duree) {
        if (index < 0 || index >= taches.size()) {
            return false;
        }

        try {
            Tache nouvelle = new SousTache(nom, description, duree);
            taches.set(index, nouvelle);

            sauvegarder();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(int index, String nom, String description, int duree, LocalDate dateDebut, LocalDate dateEcheance) {
        if (index < 0 || index >= taches.size()) {
            return false;
        }

        try {
            Tache nouvelle = new TachePrimaire(nom, description, duree, dateDebut, dateEcheance);
            taches.set(index, nouvelle);

            sauvegarder();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ##############
    // ### DELETE ###
    // ##############
    @Override
    public boolean delete(int index) {
        if (index < 0 || index >= taches.size()) {
            return false;
        }

        taches.remove(index);
        sauvegarder();
        return true;
    }

}
