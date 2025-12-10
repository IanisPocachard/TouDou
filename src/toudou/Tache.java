public class Tache {

    private String nom;
    private String description;
    private int numTache;

    public Tache(String nom, String description, int numTache) {
        this.nom = nom;
        this.description = description;
        this.numTache = numTache;
    }

    public int getNumTache() {
        return numTache;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public static int getCompteur() {
        return compteur;
    }
}
