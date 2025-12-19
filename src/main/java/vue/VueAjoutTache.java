package vue;

import controleur.ControlAddTache;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        // durée et date
        Label lblDuree = new Label("Durée (minutes)");
        lblDuree.setStyle("-fx-font-weight: bold;");
        TextField tfDuree = new TextField();

        Label lblDate = new Label("Échéance");
        lblDate.setStyle("-fx-font-weight: bold;");
        DatePicker dpEcheance = new DatePicker();

        //valider
        Button btnValider = new Button("AJOUTER");
        btnValider.setStyle("-fx-background-color: #4169E1; -fx-text-fill: white; -fx-font-weight: bold;");
        btnValider.setMaxWidth(Double.MAX_VALUE);

        // --- LIEN AVEC LE CONTROLEUR ---
        // On réutilise ton contrôleur existant !
        // Note: taDescription est une TextArea, mais le contrôleur veut un TextField ?
        // -> Pour faire simple, on passe un TextField caché ou on adaptera le contrôleur plus tard.
        // Pour l'instant, astuce : on créé un TextField factice pour que ça compile si tu ne veux pas changer le contrôleur
        // MAIS l'idéal est de modifier ControlAddTache pour accepter TextArea.
        // Ici je triche un peu : je passe tfDuree (TextField) c'est ok.
        // Pour la description, le ControlAddTache attend un TextField, donc on va adapter le contrôleur juste après.

        ControlAddTache controleur = new ControlAddTache(modele, tfNom, taDescription, tfDuree, dpEcheance, this);
        btnValider.setOnAction(controleur);

        layout.getChildren().addAll(
                lblNom, tfNom,
                lblDesc, taDescription,
                lblDuree, tfDuree,
                lblDate, dpEcheance,
                new Separator(),
                btnValider
        );

        Scene scene = new Scene(layout, 400, 500);
        this.setScene(scene);
    }
}