package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import modele.Modele;
import modele.TachePrimaire;
import java.time.ZoneId;
import java.util.Date;

public class ControlAddTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TextField champNom;
    private TextField champDescription;
    private TextField champDuree;
    private DatePicker champDateEcheance;

    public ControlAddTache(Modele modele, TextField nom, TextField desc, TextField duree, DatePicker dateFin) {
        this.modele = modele;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
        this.champDateEcheance = dateFin;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            String nom = champNom.getText().trim();
            String description = champDescription.getText().trim();
            String dureeTexte = champDuree.getText().trim();

            if (nom.isEmpty()) {
                throw new Exception("Le nom de la tâche ne peut pas être vide.");
            }
            if (dureeTexte.isEmpty()) {
                throw new Exception("La durée ne peut pas être vide.");
            }

            int duree;
            try {
                duree = Integer.parseInt(dureeTexte);
            } catch (NumberFormatException e) {
                throw new Exception("La durée doit être un nombre entier (ex: 60).");
            }

            Date dateDebut = new Date();
            Date dateFin = new Date();

            if (champDateEcheance.getValue() != null) {
                dateFin = Date.from(champDateEcheance.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            TachePrimaire nouvelleTache = new TachePrimaire(nom, description, duree, dateDebut, dateFin);
            modele.ajouterTache(nouvelleTache);

            champNom.clear();
            champDescription.clear();
            champDuree.clear();
            champDateEcheance.setValue(null);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Impossible d'ajouter la tâche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}