import java.util.Date;

public class TachePrimaire extends Tache{
    private Date dateDebut;
    private Date dateEcheance;
    private boolean archivee;

    public TachePrimaire(String nom, String description, int numTache, Date dateDebut, Date dateEcheance) {
        super(nom, description, numTache);
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.archivee = false;
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
}
