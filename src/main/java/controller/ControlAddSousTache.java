package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Modele;
import modele.SousTache;
import modele.Tache;
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

            if (nom.isEmpty())
                throw new Exception("Le nom est obligatoire !");

            if (dureeTexte.isEmpty())
                throw new Exception("La durée est obligatoire !");

            int dureeSousTache;
            try {
                dureeSousTache = Integer.parseInt(dureeTexte);
            } catch (NumberFormatException e) {
                throw new Exception("La durée doit être un nombre !");
            }

            if (dureeSousTache <= 0)
                throw new Exception("La durée doit être positive !");

            int sommeDurees = 0;
            for (Tache t : parent.getDependances()) {
                if (t instanceof SousTache) {
                    sommeDurees += t.getDuree();
                }
            }

            if (sommeDurees + dureeSousTache > parent.getDuree()) {
                throw new Exception(
                        "La somme des durées des sous-tâches (" +
                                (sommeDurees + dureeSousTache) +
                                ") dépasse la durée de la tâche parente (" +
                                parent.getDuree() + ")."
                );
            }

            SousTache st = new SousTache(nom, description, dureeSousTache);
            modele.ajouterSousTache(parent, st);
            fenetre.close();

            this.modele.logger("Sous-tache '" + st.getNom() + "' a été créée");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de création de sous-tâche");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}