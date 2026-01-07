package vue;

import controller.ControlAddDependance;
import controller.ControlAddSousTache;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

public class VueAjoutDependance extends Stage {

    public VueAjoutDependance(Modele modele, TachePrimaire tache1) {
        this.setTitle("Ajouter une dépendance à : " + tache1.getNom());
        this.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label lblTache = new Label("Sélectionnez la dépendance à appliquer : ");
        ComboBox<Tache> cbTaches = new ComboBox<>();
        for (Tache t : modele.getTaches()) {
            if (t != tache1) {
                cbTaches.getItems().add(t);
            }
        }

        Button btnValider = new Button("Ajouter");

        // On connecte le contrôleur spécifique aux sous-tâches
        ControlAddDependance controleur = new ControlAddDependance(modele, tache1, cbTaches, this);
        btnValider.setOnAction(controleur);

        layout.getChildren().addAll(lblTache, cbTaches, btnValider);

        Scene scene = new Scene(layout);
        this.setScene(scene);
        this.setMaximized(true);
    }

}
