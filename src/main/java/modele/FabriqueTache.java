package modele;

import java.util.Date;

public interface FabriqueTache {
    Tache fab(String nom, String description, int duree, Date dateDebut, Date dateEcheance) throws Exception;
}