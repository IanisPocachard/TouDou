package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modele.Modele;
import modele.TachePrimaire;
import java.time.ZoneId;
import java.util.Date;

public class ControlAddTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TextField champNom;
    private TextArea champDescription;
    private TextField champDuree;
    private DatePicker champDateEcheance;
    private Stage fenetre;

    // Constructeur
    public ControlAddTache(Modele modele, TextField nom, TextArea desc, TextField duree, DatePicker dateFin, Stage fenetre) {
        this.modele = modele;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
        this.champDateEcheance = dateFin;
        this.fenetre = fenetre;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            String nom = champNom.getText().trim();
            String description = champDescription.getText().trim();
            String dureeTexte = champDuree.getText().trim();

            if (nom.isEmpty()) throw new Exception("Le nom est vide.");
            if (dureeTexte.isEmpty()) throw new Exception("La durée est vide.");

            int duree;
            try {
                duree = Integer.parseInt(dureeTexte);
            } catch (NumberFormatException e) {
                throw new Exception("Durée invalide.");
            }

            Date dateDebut = new Date();
            Date dateFin = new Date();
            if (champDateEcheance.getValue() != null) {
                dateFin = Date.from(champDateEcheance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            TachePrimaire nouvelleTache = new TachePrimaire(nom, description, duree, dateDebut, dateFin);
            modele.ajouterTache(nouvelleTache);

            fenetre.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}