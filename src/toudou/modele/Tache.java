package modele;

public class Tache {

    private String nom;
    private String description;
    private int numTache;

    public Tache(String nom, String description, int numTache) throws Exception{
        if ((nom instanceof String) && (description instanceof String) && (numTache > 0)) {
            this.nom = nom;
            this.description = description;
            this.numTache = numTache;
        }
        else {
            throw new Exception("Vous tenez de créer une nouvelle tâche avec des mauvais paramètres !");
        }
    }

    /**
     * renvoie le numéro de la tache
     * @return numTache
     */
    public int getNumTache() {
        return numTache;
    }

    /**
     * renvoie le nom de la tache
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * renvoie la description de la tache
     * @return description
     */
    public String getDescription() {
        return description;
    }

}
