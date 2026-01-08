package modele.manager;

import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface Manager {

    public void create(Tache tache);

    public Tache read(int index);

    public List<Tache> readAll();

    public boolean update(Tache tache, String nom, String description, int duree);

    public boolean update(TachePrimaire tache, String nom, String description, int duree, LocalDate dateDebut, LocalDate dateEcheance);

    public boolean delete(Tache tache);

}
