package modele.fabrique;

import modele.Tache;

public interface FabriqueTache {
    Tache fab(String nom, String description, int duree) throws Exception;
}