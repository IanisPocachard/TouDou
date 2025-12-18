package modele;

import java.io.Serializable;

public abstract class Tache implements Serializable {

    private String nom;
    private String description;
    private int duree;
    protected int id;

    private static int compteur=0;



    // Pour éviter les problèmes de sérialisation
    private static final long serialVersionUID = 1L;

    public Tache(String nom, String description, int duree) throws Exception {
        if ((nom instanceof String) && (description instanceof String)) {
            this.nom = nom;
            this.description = description;
            this.duree = duree;
        } else {
            throw new Exception("Vous tenez de créer une nouvelle tâche avec des mauvais paramètres !");
        }
        this.id=compteur++;
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
     * renvoie la durée de la tâche
     * @return duree
     */
    public int getDuree(){return duree;}

    public int getId() {
        return id;
    }

    public abstract void setEtat(int netat)throws Exception;

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

    public abstract boolean getValide();

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
