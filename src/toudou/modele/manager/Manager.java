package modele.manager;

import modele.Tache;

import java.util.Date;
import java.util.List;

public interface Manager {

    public void create(Tache tache);

    public Tache read(int index);

    public List<Tache> readAll();

    public boolean update(int index, String nom, String description, int duree);

    public boolean update(int index, String nom, String description, int duree, Date dateDebut, Date dateEcheance);

    public boolean delete(int index);

}
