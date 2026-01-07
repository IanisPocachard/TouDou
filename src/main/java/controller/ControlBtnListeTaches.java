package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Modele;
import vue.VueListe;

public class ControlBtnListeTaches implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControlBtnListeTaches(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        VueListe vueListe = new VueListe(modele);
        Stage stage = new Stage();
        stage.setScene(new Scene(vueListe));
        stage.setTitle("TouDou - Liste des tâches");
        stage.show();
    }

}
