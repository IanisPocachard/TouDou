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
    public boolean update(Tache tache, String nom, String description, int duree) {
        if (tache == null) return false;

        tache.setNom(nom);
        tache.setDescription(description);
        tache.setDuree(duree);

        sauvegarder();
        return true;
    }

    @Override
    public boolean update(TachePrimaire tache, String nom, String description, int duree, LocalDate dateDebut, LocalDate dateEcheance) {
        if (tache == null) return false;

        tache.setNom(nom);
        tache.setDescription(description);
        tache.setDuree(duree);
        tache.setDateDebut(dateDebut);
        tache.setDateEcheance(dateEcheance);

        sauvegarder();
        return true;
    }

    // ##############
    // ### DELETE ###
    // ##############
    @Override
    public boolean delete(Tache tache) {
        if (tache == null) return false;

        boolean removed = taches.remove(tache);
        if (removed) {
            sauvegarder();
        }
        return removed;
    }

}
