package modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TachePrimaire extends Tache {
    private Date dateDebut;
    private Date dateEcheance;
    private boolean archivee;
    //Partie composite
    private List<Tache> dependances;

    public TachePrimaire(String nom, String description, int numTache, int duree, Date dateDebut, Date dateEcheance) throws Exception{
        super(nom, description, duree);
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.archivee = false;
        this.dependances = new ArrayList<Tache>();
    }

    public void ajoutDependance(Tache tache) {
        this.dependances.add(tache);
    }
    /**
     * recupere la date de debut de la tâche
     * @return dateDebut
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * recupere la date de fin de la tâche
     * @return dateEcheance
     */
    public Date getDateEcheance() {
        return dateEcheance;
    }

    /**
     * retourne vrai si la tache est archivée
     * @return archivee
     */
    public boolean isArchivee() {
        return archivee;
    }

    public String toString(){
        return this.toString(0);
    }

    @Override
    public String toString(int indent) {
        String res = super.toString(indent);

        if(!this.dependances.isEmpty()){
            res+= "\t".repeat(indent) + "Dépendances : " + '\n';
        }

        for (Tache t : dependances) {
            res += t.toString(indent + 1);
        }

        return res;
    }
}
