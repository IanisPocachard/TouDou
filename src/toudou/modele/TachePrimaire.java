package modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TachePrimaire extends Tache {
    private Date dateDebut;
    private Date dateEcheance;
    private boolean archivee;
    private int etat;

    public static final int A_FAIRE=1;
    public static final int EN_COURS=2;
    public static final int A_TESTER=3;
    public static final int VALIDEE=4;

    //Partie composite
    private List<Tache> dependances;

    public TachePrimaire(String nom, String description, int duree, Date dateDebut, Date dateEcheance) throws Exception{
        super(nom, description, duree);
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.archivee = false;
        this.dependances = new ArrayList<Tache>();
        this.etat=A_FAIRE;
    }

    public void ajoutDependance(Tache tache) {
        if (tache == this) throw new IllegalArgumentException("Une tâche ne peut dépendre d'elle-même.");
        if (!dependances.contains(tache)) dependances.add(tache);
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

    public List<Tache> getDependances() {
        return dependances;
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

    public void setEtat(int netat) throws Exception {
        if (netat < 1 || netat > 4) { //si l'état n'es pas un état valide
            throw new Exception("Etat invalide"); // on renvoie une erreur
        }
        if (netat == VALIDEE) { // si l'état est l'état VALIDEE
            for (Tache t : dependances) { // on parcours les dépendances
                if (!t.getValide()) { // si une dépendance n'est pas en état VALIDEE
                    throw new Exception("Impossible de valider : une dépendance n'est pas validée.");// on renvoie une exception
                }
            }
        }
        this.etat = netat; // on prend l'état passé en parametre
    }

    public int getEtat(){
        return this.etat;
    }

    public boolean getValide(){
            return this.etat == TachePrimaire.VALIDEE;
    }
}
