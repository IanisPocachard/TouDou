package modele.manager;

import modele.Tache;

public interface Manager {

    public void create(Tache tache);

    public Tache read(int index);

    public boolean update(int index, String nom, String description, int duree);

    public boolean delete(int index);

}
