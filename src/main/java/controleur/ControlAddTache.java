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

public class ControlBtnAddTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TextField champNom;
    private TextField champDescription;
    private TextField champDuree;
    private DatePicker champDateEcheance;

    public ControlBtnAddTache(Modele modele, TextField nom, TextField desc, TextField duree, DatePicker dateFin) {
        this.modele = modele;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
        this.champDateEcheance = dateFin;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            String nom = champNom.getText();
            String description = champDescription.getText();
            int duree = Integer.parseInt(champDuree.getText());

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible d'ajouter la tâche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}