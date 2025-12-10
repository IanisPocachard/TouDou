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
    public String toString(int indent){
        String res="\t".repeat(indent)+ "Nom de la tache : "+nom+ '\n' + "\t".repeat(indent) + "Description de la  tache : "+description+ '\n' + "\t".repeat(indent) + "Durée de la tache : "+ duree +
                '\n';
        return res;
    }

    public String toString(){
        return this.toString(0);
    }

}
