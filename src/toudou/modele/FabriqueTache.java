package modele;

import java.util.Date;

public class FabriqueTache {

    public Tache creerTache(String nom, String description, Date dateEcheance, int tempsNecessaire) throws Exception {
        return new Tache(nom, description, dateEcheance, tempsNecessaire);
    }
}
