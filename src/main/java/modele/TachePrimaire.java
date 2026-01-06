package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TachePrimaire extends Tache {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateEcheance;
    private int etat;


    public static final int A_FAIRE=1;
    public static final int EN_COURS=2;
    public static final int A_TESTER=3;
    public static final int VALIDEE=4;
    public static final int ARCHIVEE=5;

    //Partie composite
    private List<Tache> dependances;

    public TachePrimaire(String nom, String description, int duree, LocalDate dateDebut, LocalDate dateFin, LocalDate dateEcheance) throws Exception {
        super(nom, description, duree);
        if (dateDebut == null || dateFin == null || dateEcheance == null) {
            throw new Exception("Les dates ne peuvent pas être nulles");
        }
        if (dateFin.isBefore(dateDebut)) {
            throw new Exception("La date de fin doit être après la date de début");
        }
        if (dateEcheance.isBefore(dateFin)) {
            throw new Exception("La date d’échéance doit être après ou égale à la date de fin");
        }
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateEcheance = dateEcheance;
        this.dependances = new ArrayList<>();
        this.etat = A_FAIRE;
    }

    public void ajoutDependance(Tache tache) {
        if (tache == this) throw new IllegalArgumentException("Une tâche ne peut dépendre d'elle-même.");
        if (!dependances.contains(tache)) dependances.add(tache);
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
     * @return dateFin
     */
    public LocalDate getDateFin() {
        return dateFin;
    }

    /**
     * recupere la date d'echance de la tâche
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
        return this.etat==ARCHIVEE;
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
        if (netat < 1 || netat > 5) { //si l'état n'es pas un état valide
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


    public void archiver() throws Exception{
        if(this.getEtat() == VALIDEE){
            this.setEtat(ARCHIVEE);
        }
        else throw new Exception("Ne peut pas être archivée car n'est pas validée");
    }

    public boolean getValide(){
            return this.etat == TachePrimaire.VALIDEE;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) throws Exception {
        if (dateFin.isBefore(dateDebut) || dateFin.isAfter(dateEcheance))
            throw new Exception("Date de fin invalide.");
        this.dateFin = dateFin;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getNomEtat(int etat){
        switch (etat) {
            case ARCHIVEE:
                return "Archivée";
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
