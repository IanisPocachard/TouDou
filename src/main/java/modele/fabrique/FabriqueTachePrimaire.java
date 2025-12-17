package modele.fabrique;

import modele.Tache;
import modele.TachePrimaire;

import java.util.Date;

public class FabriqueTachePrimaire implements FabriqueTache {

    @Override
    public Tache fab(String nom, String description, int duree) throws Exception {
        return new TachePrimaire(nom, description, duree, null, null);
    }

    public static TachePrimaire personnaliser(TachePrimaire tache, Date dateDebut, Date dateEcheance) {
        tache.setDateDebut(dateDebut);
        tache.setDateEcheance(dateEcheance);
        return tache;
    }
}