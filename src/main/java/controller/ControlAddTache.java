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
    private Stage fenetre;

    // Constructeur
    public ControlAddTache(Modele modele, TextField nom, TextArea desc, TextField duree, DatePicker dateDebut, DatePicker dateFin, Stage fenetre) {
        this.modele = modele;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
        this.champDateDebut = dateDebut;
        this.champDateFin = dateFin;
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

            if (duree <= 0) {
                throw new Exception("La durée doit être strictement positive !");
            }

            LocalDate dateDebut = champDateDebut.getValue();
            LocalDate dateFin = champDateFin.getValue();

            if (dateDebut == null || dateFin == null) {
                throw new Exception("Les dates de début et de fin doivent être renseignées !");
            }

            if (dateFin.isBefore(dateDebut)) {
                throw new Exception("La date de fin doit être après la date de début !");
            }

            long nbJours = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;

            if (nbJours < duree) {
                throw new Exception("La durée dépasse l’intervalle entre la date de début et la date de fin !");
            }

            TachePrimaire nouvelleTache = new TachePrimaire(nom, description, duree, dateDebut, dateFin);

            modele.ajouterTache(nouvelleTache);

            modele.logger("Tâche '" + nouvelleTache.getNom() + "' créée");

            fenetre.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}