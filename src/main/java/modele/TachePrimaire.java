package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TachePrimaire extends Tache {
    private LocalDate dateDebut;
    private LocalDate dateEcheance;
    private int etat;
    private boolean archivee = false;


    public static final int A_FAIRE=1;
    public static final int EN_COURS=2;
    public static final int A_TESTER=3;
    public static final int VALIDEE=4;

    //Partie composite
    private List<Tache> dependances;

    public TachePrimaire(String nom, String description, int duree, LocalDate dateDebut, LocalDate dateEcheance) throws Exception {
        super(nom, description, duree);
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.dependances = new ArrayList<Tache>();
        this.etat = A_FAIRE;
        this.archivee = false;
    }

    public void ajoutDependance(Tache tache) throws Exception {
        if (tache == null)
            throw new Exception("La dépendance ne peut pas être nulle");

        if (tache == this)
            throw new Exception("Une tâche ne peut pas dépendre d'elle-même");

        // Vérification de cycle : est-ce que tache dépend déjà de this ?
        if (parcoursCycle(tache, this))
            throw new Exception("Cycle détecté dans les dépendances");

        if (!dependances.contains(tache))
            dependances.add(tache);
    }

    private boolean parcoursCycle(Tache courante, Tache cible) {
        return parcoursCycleRecursif(courante, cible, new ArrayList<>());
    }

    private boolean parcoursCycleRecursif(Tache courante, Tache cible, List<Tache> visitees) {
        if (courante == cible)
            return true;

        if (visitees.contains(courante))
            return false;

        visitees.add(courante);

        if (courante instanceof TachePrimaire) {
            TachePrimaire tp = (TachePrimaire) courante;
            for (Tache t : tp.getDependances()) {
                if (parcoursCycleRecursif(t, cible, visitees))
                    return true;
            }
        }

        return false;
    }

    /**
     * recupere la date de debut de la tâche
     * @return dateDebut
     */
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    /**
     * recupere la date de fin de la tâche
     * @return dateEcheance
     */
    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public List<Tache> getDependances() {
        return dependances;
    }

    public int getId() {
        return id;
    }

    /**
     * retourne vrai si la tache est archivée
     * @return archivee
     */
    public boolean isArchivee() {
        return this.archivee;
    }

    public void setArchivee(boolean archivee) {
        this.archivee = archivee;
    }

    public String toString(){
        return this.toString(0);
    }

    @Override
    public String toString(int indent) {
        String res = super.toString(indent);

        boolean contientDependance = false;
        for (Tache t : this.dependances) {
            if (t instanceof TachePrimaire) {
                contientDependance = true;
                break;
            }
        }

        if (contientDependance) {
            res+= "\t".repeat(indent) + "Dépendances : " + '\n';
        }

        for (Tache t : dependances) {
            if (t instanceof TachePrimaire) {
                res += "\t" + t.getNom();
            }
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

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getNomEtat(int etat){
        switch (etat) {
            case VALIDEE:
                return "Valide";
            case A_FAIRE:
                return "A faire";
            case EN_COURS:
                return "En cours";
            case A_TESTER:
                return "A tester";
            default:
                return "Etat invalide.";
        }
    }

}
