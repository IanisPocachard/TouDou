package modele;

import java.util.Date;

public class FabriqueSousTache implements FabriqueTache {

    @Override
    public Tache fab(String nom, String description, int duree) throws Exception {
        return new SousTache(nom, description, duree);
    }
}