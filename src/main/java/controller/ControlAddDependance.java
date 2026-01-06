package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;

public class ControlAddDependance implements EventHandler<ActionEvent> {

    private Modele modele;
    private TachePrimaire tache1;
    private ComboBox<Tache> cbTaches;
    private Stage fenetre;

    public ControlAddDependance(Modele modele, TachePrimaire tache1, ComboBox<Tache> cbTaches, Stage fenetre) {
        this.modele = modele;
        this.tache1 = tache1;
        this.cbTaches = cbTaches;
        this.fenetre = fenetre;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            Tache tache2 = cbTaches.getValue();

            if (tache2 == null)
                throw new Exception("Veuillez sélectionner une tâche.");

            if (!(tache2 instanceof TachePrimaire))
                throw new Exception("Une dépendance ne peut être créée qu'entre tâches primaires.");

            TachePrimaire tp2 = (TachePrimaire) tache2;

            if (tp2 == tache1)
                throw new Exception("Une tâche ne peut pas dépendre d'elle-même.");

            LocalDate debutA = tache1.getDateDebut();
            LocalDate finA   = tache1.getDateEcheance();
            LocalDate debutB = tp2.getDateDebut();
            LocalDate finB   = tp2.getDateEcheance();

            if (debutA.equals(debutB) && finA.equals(finB)) {
                throw new Exception(
                        "Deux tâches ayant le même intervalle temporel ne peuvent pas être liées par une dépendance."
                );
            }

            if (finB.isAfter(debutA)) {
                throw new Exception(
                        "Dépendance invalide : la tâche dépendante commence avant la fin de la tâche dont elle dépend."
                );
            }

            tache1.ajoutDependance(tp2);
            modele.notifierObservateurs();
            fenetre.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de dépendance");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
