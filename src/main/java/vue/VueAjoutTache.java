package vue;

import controller.ControlAddTache;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Modele;

public class VueAjoutTache extends Stage {

    public VueAjoutTache(Modele modele) {
        this.setTitle("Ajouter une tâche");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // nom
        Label lblNom = new Label("Nom de la tâche");
        lblNom.setStyle("-fx-font-weight: bold;");
        TextField tfNom = new TextField();
        tfNom.setPromptText("ex : Réaliser l'emploi du temps");

        // description
        Label lblDesc = new Label("Description de la tâche");
        lblDesc.setStyle("-fx-font-weight: bold;");
        TextArea taDescription = new TextArea();
        taDescription.setPromptText("Détails de la tâche...");
        taDescription.setPrefHeight(100);

        // durée
        Label lblDuree = new Label("Durée (en jours)");
        lblDuree.setStyle("-fx-font-weight: bold;");
        TextField tfDuree = new TextField();

        // date de début
        Label lblDateDebut = new Label("Date de début");
        lblDateDebut.setStyle("-fx-font-weight: bold;");
        DatePicker dpDateDebut = new DatePicker();

        // date de fin
        Label lblDateFin = new Label("Échéance");
        lblDateFin.setStyle("-fx-font-weight: bold;");
        DatePicker dpEcheance = new DatePicker();

        // bouton valider
        Button btnValider = new Button("AJOUTER");
        btnValider.setStyle(
                "-fx-background-color: #4169E1; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold;"
        );
        btnValider.setMaxWidth(Double.MAX_VALUE);

        // contrôleur
        ControlAddTache controleur = new ControlAddTache(
                modele,
                tfNom,
                taDescription,
                tfDuree,
                dpDateDebut,
                dpEcheance,
                this
        );
        btnValider.setOnAction(controleur);

        layout.getChildren().addAll(
                lblNom, tfNom,
                lblDesc, taDescription,
                lblDuree, tfDuree,
                lblDateDebut, dpDateDebut,
                lblDateFin, dpEcheance,
                new Separator(),
                btnValider
        );

        Scene scene = new Scene(layout, 600, 800);
        this.setScene(scene);
    }
}