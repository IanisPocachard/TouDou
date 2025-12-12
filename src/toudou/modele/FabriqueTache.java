package modele;

import java.util.Date;

public class FabriqueTache {

    public Tache creerTache(String nom, String description, int duree, Date dateDebut,
                            Date dateEcheance) throws Exception {
        return new TachePrimaire(nom, description, duree, dateDebut, dateEcheance);
    }

    
}
