package vue;

import controller.ControlAddSousTache;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Modele;
import modele.TachePrimaire;

public class VueAjoutSousTache extends Stage {

    public VueAjoutSousTache(Modele modele, TachePrimaire parent) {
        this.setTitle("Ajouter une sous-tâche à : " + parent.getNom());
        this.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label lblNom = new Label("Nom de la sous-tâche");
        TextField tfNom = new TextField();

        Label lblDesc = new Label("Description");
        TextArea taDescription = new TextArea();
        taDescription.setPrefHeight(80);

        Label lblDuree = new Label("Durée");
        TextField tfDuree = new TextField();

        Button btnValider = new Button("Ajouter");

        // On connecte le contrôleur spécifique aux sous-tâches
        ControlAddSousTache controleur = new ControlAddSousTache(modele, parent, tfNom, taDescription, tfDuree, this);
        btnValider.setOnAction(controleur);

        layout.getChildren().addAll(lblNom, tfNom, lblDesc, taDescription, lblDuree, tfDuree, btnValider);

        Scene scene = new Scene(layout);
        this.setScene(scene);
        this.setMaximized(true);
    }
}