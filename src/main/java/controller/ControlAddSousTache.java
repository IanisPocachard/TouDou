package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Modele;
import modele.SousTache;
import modele.TachePrimaire;

public class ControlAddSousTache implements EventHandler<ActionEvent> {

    private Modele modele;
    private TachePrimaire parent;
    private TextField champNom;
    private TextArea champDescription;
    private TextField champDuree;
    private Stage fenetre;

    public ControlAddSousTache(Modele modele, TachePrimaire parent, TextField nom, TextArea desc, TextField duree, Stage fenetre) {
        this.modele = modele;
        this.parent = parent;
        this.champNom = nom;
        this.champDescription = desc;
        this.champDuree = duree;
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

            int duree = Integer.parseInt(dureeTexte);

            // creation soustache
            SousTache st = new SousTache(nom, description, duree);

            parent.ajoutDependance(st);

            modele.notifierObservateurs();

            fenetre.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}