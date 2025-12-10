package modele;

public class Tache {

    private String nom;
    private String description;
    private int numTache;
    private int duree;

    public Tache(String nom, String description, int numTache, int duree) {
        this.nom = nom;
        this.description = description;
        this.numTache = numTache;
        this.duree = duree;
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

    /**
     * Retourne sous format texte une tâche
     * @return res : état de la tâche
     */
    public String toString(){
        String res="Nom de la tache : "+nom+ '\n' + "Description de la  tache : "+description+ '\n' + "Durée de la tache : "+ duree +
                '\n';
        return res;
    }

}
