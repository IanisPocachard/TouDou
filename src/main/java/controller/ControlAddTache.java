package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.Modele;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ControlAddTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TextField champNom;
    private TextArea champDescription;
    private TextField champDuree;
    private DatePicker champDateDebut;
    private DatePicker champDateFin;
    private DatePicker champDateEcheance;
    private Stage fenetre;

    public ControlAddTache(Modele modele, TextField nom, TextArea desc, TextField duree, DatePicker dateDebut, DatePicker dateFin, DatePicker dateEcheance, Stage fenetre) {
        this.modele = modele;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
        this.champDateDebut = dateDebut;
        this.champDateFin = dateFin;
        this.champDateEcheance = dateEcheance;
        this.fenetre = fenetre;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            String nom = champNom.getText().trim();
            String description = champDescription.getText().trim();

            if (nom.isEmpty())
                throw new Exception("Le nom de la tâche est obligatoire !");

            int duree;
            try {
                duree = Integer.parseInt(champDuree.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("La durée doit être un nombre !");
            }
            if (duree < 0){
                throw new Exception("La durée doit être positive");
            }

            LocalDate debut = champDateDebut.getValue();
            LocalDate fin = champDateFin.getValue();
            LocalDate echeance = champDateEcheance.getValue();

            if (debut == null || fin == null || echeance == null)
                throw new Exception("Toutes les dates doivent être renseignées !");

            if (fin.isBefore(debut))
                throw new Exception("La date de fin doit être après la date de début !");

            if (echeance.isBefore(fin))
                throw new Exception("La date d’échéance doit être après ou égale à la date de fin !");

            long nbJours = ChronoUnit.DAYS.between(debut, fin);
            if (nbJours < duree)
                throw new Exception("La durée dépasse l’intervalle entre début et fin !");

            TachePrimaire tache = new TachePrimaire(
                    nom,
                    description,
                    duree,
                    debut,
                    fin,
                    echeance
            );

            tache.setDateEcheance(echeance);

            modele.ajouterTache(tache);
            fenetre.close();

            this.modele.logger("Tâche '" + tache.getNom() + "' a été créée");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}